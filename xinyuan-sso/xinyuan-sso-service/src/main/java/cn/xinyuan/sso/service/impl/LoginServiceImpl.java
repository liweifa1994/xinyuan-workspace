package cn.xinyuan.sso.service.impl;

import cn.xinyuan.common.util.XinYuanResult;
import cn.xinyuan.mapper.TbUserMapper;
import cn.xinyuan.pojo.TbUser;
import cn.xinyuan.pojo.TbUserExample;
import cn.xinyuan.sso.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * User:josli li
 * Date:2017/8/26
 * Time:19:35
 * Mail:josli@kargocard.com
 */
@Service
public class LoginServiceImpl implements LoginService {
    private Logger log = LoggerFactory.getLogger(LoginServiceImpl.class);

    @Autowired
    private TbUserMapper userMapper ;

    @Autowired
    private RedisTemplate<String,TbUser> redisTemplate;
    @Value("#{xinyuan_session_user_token_info}")
    protected String userKey;
    @Value("${SESSION_EXPIRE}")
    private Long SESSION_EXPIRE;
    @Override
    public XinYuanResult userLogin(String username, String password) {
        //通用的校验

        //判断用户注册的方式
        TbUserExample userExample = new TbUserExample();
        TbUserExample.Criteria userCriteria = userExample.createCriteria();
        userCriteria.andUsernameEqualTo(username);
        List<TbUser> userList = userMapper.selectByExample(userExample);
        if (userList == null || userList.size() ==0){
            XinYuanResult.build(400, "用户找不到");
        }
        TbUser user = userList.get(0);
        String userPassword = user.getPassword();
        //验证密码是否正确
       if (userPassword.equals(DigestUtils.md5DigestAsHex(password.getBytes()))) {
           String token = UUID.randomUUID().toString();
           user.setPassword(null);
           redisTemplate.opsForValue().set(userKey + ":" + token,user);
           redisTemplate.expire(userKey + ":" + token,SESSION_EXPIRE, TimeUnit.SECONDS);
           return XinYuanResult.ok(token);
       }else {
           XinYuanResult.build(400, "用户名或密码错误");
       }
        return null;
    }

    @Override
    public XinYuanResult userLogout(String token) {
        return null;
    }
}
