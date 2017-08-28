package cn.xinyuan.order.test;

import cn.xinyuan.mapper.SequenceValueItemMapper;
import cn.xinyuan.pojo.SequenceValueItem;
import cn.xinyuan.service.SequenceService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * User:josli li
 * Date:17/8/28
 * Time:下午3:09
 * Mail:josli@kargocard.com
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:spring/applicationContext-redis.xml")
public class RedisTest {
//
   @Autowired
    private RedisTemplate<String,String> redisTemplate;

 //   @Autowired

  //  private JedisConnectionFactory jedisConnectionFactory;


    @Test
    public void testInc(){
       // RedisTemplate<String,String> redisTemplate = new RedisTemplate<>();
      //  redisTemplate.setConnectionFactory(jedisConnectionFactory);
      //  redisTemplate.setValueSerializer(new StringRedisSerializer());
       // redisTemplate.afterPropertiesSet();
        String key ="hello";
        ValueOperations<String, String> longValueOperations = redisTemplate.opsForValue();
        longValueOperations.set(key,"1");
        for (int i =1;i<20;i++){
            System.out.println(longValueOperations.increment(key,100));

        }
    }
}
