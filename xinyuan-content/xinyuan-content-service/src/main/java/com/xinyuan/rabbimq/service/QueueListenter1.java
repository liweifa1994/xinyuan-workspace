package com.xinyuan.rabbimq.service;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * User:josli li
 * Date:17/8/18
 * Time:下午5:00
 * Mail:josli@kargocard.com
 */

@Component
public class QueueListenter1 implements MessageListener {

    public void onMessage(Message msg) {
        try{
            System.out.println("hello kargo");
            System.out.println(new String(msg.getBody()));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}