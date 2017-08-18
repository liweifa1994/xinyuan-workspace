package cn.xinyuan.content.test;

import cn.xinyuan.common.util.JSONUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

/**
 * User:josli li
 * Date:17/8/18
 * Time:上午10:10
 * Mail:josli@kargocard.com
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:spring/applicationContext-redis.xml")
public class RedisTmlpateTest {
    private Logger logger = LoggerFactory.getLogger(RedisTest.class);

    @Autowired
    private RedisTemplate<String,Order> redisTemplate;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate2;
    @Test
    public void testRedisConnection(){
//        ValueOperations<String, Object> opsForValue = redisTemplate.opsForValue();
//        ZSetOperations<String, Object> opsForZSet = redisTemplate.opsForZSet();
//        HashOperations<String, Object, Object> opsForHash = redisTemplate.opsForHash();
//        ListOperations<String, Object> listOperations = redisTemplate.opsForList();
//        SetOperations<String, Object> opsForSet = redisTemplate.opsForSet();


    }

    @Test
    public void testSave(){
        //   ValueOperations<String, Order> opsForValue = redisTemplate.opsForValue();
        ValueOperations<String, Order> valueOper = redisTemplate.opsForValue();
        Order order = new Order("1", 123 + "", 45, new Date());
        valueOper.set(order.getOrderNo(),order);

    }

    @Test
    public void testRead(){
        //   ValueOperations<String, Order> opsForValue = redisTemplate.opsForValue();
        ValueOperations<String, Order> valueOper = redisTemplate.opsForValue();
     //   Order order = new Order("1", 123 + "", 45, new Date());
        System.out.println(JSONUtils.ObjToJson(valueOper.get("123")));
    }

    @Test
    public void testdel(){
        //   ValueOperations<String, Order> opsForValue = redisTemplate.opsForValue();
        ValueOperations<String, Order> valueOper = redisTemplate.opsForValue();
        RedisOperations<String, Order> operations = valueOper.getOperations();
        //   Order order = new Order("1", 123 + "", 45, new Date());
        operations.delete("123");
        System.out.println(JSONUtils.ObjToJson(valueOper.get("123")));
    }

    @Test
    public void test2(){
        ValueOperations<String, Order> valueOper = redisTemplate.opsForValue();
        Order order = new Order("1", 123 + "", 45, new Date());
        valueOper.set(order.getOrderNo(),order);
        System.out.println(JSONUtils.ObjToJson(valueOper.get("123")));
        ValueOperations<String, Object> opsForValue = redisTemplate2.opsForValue();
        opsForValue.set("1","hello world");
        System.out.println(opsForValue.get("1"));


    }


}
