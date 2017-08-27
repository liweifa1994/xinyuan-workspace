package cn.xinyuan.sso.service.impl;

import cn.xinyuan.common.util.XinYuanResult;
import cn.xinyuan.common.util.exception.ExceptionFactoryUtil;
import cn.xinyuan.common.util.exception.XinYuanCommonUtils;
import cn.xinyuan.mapper.TbUserMapper;
import cn.xinyuan.pojo.TbUser;
import cn.xinyuan.pojo.TbUserExample;
import cn.xinyuan.sso.service.RegisterService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

/**
 * User:josli li
 * Date:2017/8/26
 * Time:19:35
 * Mail:josli@kargocard.com
 */
@Service
public class RegisterServiceImpl implements RegisterService {

    private Logger log = LoggerFactory.getLogger(RegisterServiceImpl.class);

    @Autowired
    private TbUserMapper userMapper ;
    @Override
    public XinYuanResult checkData(String param) {
        if (StringUtils.isBlank(param)){
            String errorInfo = "参数不能为空";
            log.error(errorInfo);
            ExceptionFactoryUtil.createDataNullException(errorInfo);
        }
        //判断用户注册的方式
        TbUserExample userExample = new TbUserExample();
        TbUserExample.Criteria userCriteria = userExample.createCriteria();
       if (XinYuanCommonUtils.isEmail(param)){
           userCriteria.andEmailEqualTo(param);
       } else if (XinYuanCommonUtils.isMobileNO(param)){
           userCriteria.andPhoneEqualTo(param);
       }else {
           userCriteria.andUsernameEqualTo(param);
       }
       try {
           List<TbUser> userList = userMapper.selectByExample(userExample);
           //表示用户已经存在
           if (userList != null && userList.size()>0){
               return XinYuanResult.ok(false);
           }
       }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage());
           return XinYuanResult.ok(false);
       }

        return XinYuanResult.ok(true);
    }

    @Override
    public XinYuanResult register(TbUser user) {
        if (StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword())
                || StringUtils.isBlank(user.getPhone())) {
            return XinYuanResult.build(400, "用户数据不完整，注册失败");
        }
        XinYuanResult phone = checkData(user.getPhone());
        if (!(boolean) phone.getData()){
            String errorInfo = "手机号已经被使用，用户注册失败";
            log.error(errorInfo);
            return XinYuanResult.build(400, errorInfo);
        }
        XinYuanResult userName = checkData(user.getUsername());
        if (!(boolean) userName.getData()){
            String errorInfo = "用户名已经被使用，用户注册失败";
            log.error(errorInfo);
            return XinYuanResult.build(400, errorInfo);
        }
        user.setCreated(new Date());
        user.setUpdated(new Date());
        //进行加密
        String password = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        user.setPassword(password);
        try {
            userMapper.insert(user);
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage());
            return XinYuanResult.ok(false);
        }
        return  XinYuanResult.ok(true);
    }
}
