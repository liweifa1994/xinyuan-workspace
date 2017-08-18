package cn.xinyuan.content.test;

import com.xinyuan.rabbimq.service.MQProducer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * User:josli li
 * Date:17/8/18
 * Time:下午4:50
 * Mail:josli@kargocard.com
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:spring/applicationContext-rabbitmq.xml")
public class RabbitMqServiceTest {

    @Autowired
    private MQProducer mQProducerImpl;

     String queue_key = "test_queue_key";

    @Test
    public void send(){
        Map<String,Object> msg = new HashMap<String,Object>();
        msg.put("data","hello,rabbmitmq!");
        mQProducerImpl.sendDataToQueue(queue_key,msg);
    }
}
