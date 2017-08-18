package cn.xinyuan.content.test;

import cn.xinyuan.content.service.JedisClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * User:josli li
 * Date:17/8/18
 * Time:上午9:43
 * Mail:josli@kargocard.com
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:spring/applicationContext-*.xml")
public class RedisTest {
    private Logger logger = LoggerFactory.getLogger(RedisTest.class);
    @Autowired
    private JedisClient jedisClient;
    @Test
    public void testRedisSet(){
//        jedisClient.set("a","1234567");
//        jedisClient.set("b","1234567");
//        jedisClient.set("b","我是李伟发");
        System.out.println(jedisClient.get("a"));
        System.out.println(jedisClient.get("b"));
        logger.info("ok");
    }
}
