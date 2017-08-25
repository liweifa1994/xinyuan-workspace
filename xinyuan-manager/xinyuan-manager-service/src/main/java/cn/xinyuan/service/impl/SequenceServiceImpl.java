package cn.xinyuan.service.impl;

import cn.xinyuan.common.util.exception.ExceptionFactoryUtil;
import cn.xinyuan.mapper.SequenceValueItemMapper;
import cn.xinyuan.pojo.SequenceValueItem;
import cn.xinyuan.service.SequenceService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Hashtable;
import java.util.Map;

/**
 * User:josli li
 * Date:17/8/25
 * Time:上午10:06
 * Mail:josli@kargocard.com
 */

@Service
public class SequenceServiceImpl implements SequenceService{

    Map<String, SequenceBank> sequences = new Hashtable<String, SequenceBank>();
    @Autowired
     SequenceValueItemMapper sequenceValueItemMapper;
    public Long getNextSeqId(String seqName, long staggerMax) {
        if (StringUtils.isBlank(seqName)){
            ExceptionFactoryUtil.createDataFromatErrorException("seqName 不能为空");
        }
        SequenceBank bank = getBank(seqName);
        return bank.getNextSeqId(staggerMax,seqName,sequenceValueItemMapper);
    }

    public void forceBankRefresh(String seqName, long staggerMax) {
        // don't use the get method because we don't want to create if it fails
        SequenceBank bank = sequences.get(seqName);
        if (bank == null) {
            return;
        }

        bank.refresh(staggerMax,seqName,sequenceValueItemMapper);
    }
    private SequenceBank getBank(String seqName) {
        SequenceBank bank = sequences.get(seqName);

        if (bank == null) {
            synchronized(this) {
                bank = sequences.get(seqName);
                if (bank == null) {
                    bank = new SequenceBank(seqName,sequenceValueItemMapper);
                    sequences.put(seqName, bank);
                }
            }
        }
        return bank;
    }
}
class SequenceBank {
    private static Logger log = LoggerFactory.getLogger(SequenceBank.class);
    public static final long defaultBankSize = 10;
    public static final long startSeqId = 10000;
    public static final int maxTries = 5;
    public static final int minWaitMillis = 5;
    public static final int maxWaitMillis = 50;

    private long curSeqId;
    private long maxSeqId;

    public SequenceBank(String seqName,SequenceValueItemMapper sequenceValueItemMapper) {
        curSeqId = 0;
        maxSeqId = 0;
        fillBank(1, seqName,sequenceValueItemMapper);
    }

    public SequenceBank(){

    }

    public synchronized Long getNextSeqId(long staggerMax,String seqName,SequenceValueItemMapper sequenceValueItemMapper) {
        long stagger = 1;
        if (staggerMax > 1) {
            stagger = Math.round(Math.random() * staggerMax);
            if (stagger == 0) stagger = 1;
        }

        if ((curSeqId + stagger) <= maxSeqId) {
            Long retSeqId = Long.valueOf(curSeqId);
            curSeqId += stagger;
            return retSeqId;
        } else {
            fillBank(stagger, seqName,sequenceValueItemMapper);
            if ((curSeqId + stagger) <= maxSeqId) {
                Long retSeqId = Long.valueOf(curSeqId);
                curSeqId += stagger;
                return retSeqId;
            } else {
                log.info("getNextSeqId Fill bank failed, returning null");
                return null;
            }
        }
    }

    public void refresh(long staggerMax,String seqName,SequenceValueItemMapper sequenceValueItemMapper) {
        this.curSeqId = this.maxSeqId;
        this.fillBank(staggerMax, seqName,sequenceValueItemMapper);
    }


    protected synchronized void fillBank(long stagger, String seqName,SequenceValueItemMapper sequenceValueItemMapper) {
        if ((curSeqId + stagger) <= maxSeqId) return;

        long bankSize = defaultBankSize;

        long val1 = 0;
        long val2 = 0;

        int numTries = 0;

        while (val1 + bankSize != val2) {
            synchronized (this) {
                //查询数据库
                SequenceValueItem sequenceValueItem = sequenceValueItemMapper.selectByPrimaryKey(seqName);
                boolean gotVal1 = false;
                boolean gotVal2 = false;
                if (sequenceValueItem != null) {
                    val1 = sequenceValueItem.getSeqId().longValue();
                    gotVal1 = true;
                }
                if (!gotVal1) {
                    log.info("fillBank first select failed: will try to add new row, result set was empty for sequence [" + seqName + "]  is: " + Thread.currentThread().getName() + ":" + Thread.currentThread().toString());
                    SequenceValueItem tempValueItem = new SequenceValueItem();
                    tempValueItem.setSeqName(seqName);
                    tempValueItem.setSeqId(BigDecimal.valueOf(startSeqId));
                    int insert = sequenceValueItemMapper.insert(tempValueItem);
                    if (insert <= 0) {
                        log.info("No rows changed when trying insert new sequence row with this : ");
                    }
                    continue;
                }
                SequenceValueItem tempValueItem = new SequenceValueItem();
                tempValueItem.setSeqName(seqName);
                tempValueItem.setSeqId(BigDecimal.valueOf(sequenceValueItem.getSeqId().longValue()+bankSize));
                int updateCount = sequenceValueItemMapper.updateByPrimaryKey(tempValueItem);
                if (updateCount<0){
                    log.info("fillBank update failed, no rows changes for seqName: " + seqName);
                }
                sequenceValueItem = sequenceValueItemMapper.selectByPrimaryKey(seqName);
                if (sequenceValueItem != null) {
                    val2 = sequenceValueItem.getSeqId().longValue();
                    gotVal2 = true;
                }
                if (!gotVal2) {
                    ExceptionFactoryUtil.createResultException("[SequenceServiceImpl.SequenceBank.fillBank] second select failed: aborting, result set was empty for sequence: " + seqName);
                }
                if (val1 + bankSize != val2){
                    log.info("Forcing transaction rollback in sequence increment because we didn't get a clean update, ie a conflict was found, so not saving the results");
                    //强制回滚事物

                }
            }

            if (val1 + bankSize != val2) {
                //尝试几次去轮询
                if (numTries >= maxTries) {
                    String errMsg = "fillBank maxTries (" + maxTries + ") reached for seqName [" + seqName + "], giving up.";
                    log.info(errMsg);
                    return;
                }
                // collision happened, wait a bounded random amount of time then continue
                int waitTime = (new Double(Math.random() * (maxWaitMillis - minWaitMillis))).intValue() + minWaitMillis;
                log.info("fillBank Collision found for seqName [" + seqName + "], val1=" + val1 + ", val2=" + val2 + ", val1+bankSize=" + (val1 + bankSize) + ", bankSize=" + bankSize + ", waitTime=" + waitTime);
                try {
                    //等待几秒再一次运行
                    java.lang.Thread.sleep(waitTime);
                } catch (Exception e) {
                    log.info("Error waiting in sequence util,ERROR:"+e);
                    return;
                }
            }

            numTries++;
        }
        curSeqId = val1;
        maxSeqId = val2;
        log.info("Got bank of sequenced IDs for [" + seqName + "]; curSeqId=" + curSeqId + ", maxSeqId=" + maxSeqId + ", bankSize=" + bankSize);
    }
}

