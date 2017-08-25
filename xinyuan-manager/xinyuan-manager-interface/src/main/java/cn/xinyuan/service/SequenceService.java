package cn.xinyuan.service;

/**
 * User:josli li
 * Date:17/8/25
 * Time:下午2:32
 * Mail:josli@kargocard.com
 */
public interface SequenceService {
    /**
     * 获取序列值
     * @param seqName
     * @param staggerMax
     * @return
     */
    Long getNextSeqId(String seqName, long staggerMax);

    /**
     * 刷新序列值
     * @param seqName
     * @param staggerMax
     */
    void forceBankRefresh(String seqName, long staggerMax);
}
