package cn.xinyuan.sso.service.impl;

import cn.xinyuan.common.util.XinYuanResult;
import cn.xinyuan.pojo.TbUser;
import cn.xinyuan.sso.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * User:josli li
 * Date:2017/8/26
 * Time:19:36
 * Mail:josli@kargocard.com
 */
@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private RedisTemplate<String,TbUser> redisTemplate;
    @Value("#{xinyuan_session_user_token_info}")
    protected String userKey;

    @Value("#{SESSION_EXPIRE}")
    private Long SESSION_EXPIRE;
    @Override
    public XinYuanResult getUserByToken(String token) {
        ValueOperations<String, TbUser> userValueOperations = redisTemplate.opsForValue();
        String key = userKey + ":" + token;
        TbUser tbUser = userValueOperations.get(key);
        if (tbUser == null){
            return XinYuanResult.build(201, "用户登录已经过期");
        }
        redisTemplate.expire(key,SESSION_EXPIRE, TimeUnit.SECONDS);
        return XinYuanResult.ok(tbUser);
    }
}
