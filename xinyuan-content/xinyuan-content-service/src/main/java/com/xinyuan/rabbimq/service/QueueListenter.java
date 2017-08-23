package com.xinyuan.rabbimq.service;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * User:josli li
 * Date:17/8/18
 * Time:下午5:00
 * Mail:josli@kargocard.com
 */

@Component
public class QueueListenter implements MessageListener {

    public void onMessage(Message msg) {
        try{
            System.out.println("begin ");
            System.out.println(new String(msg.getBody()));
            System.out.println("end");

        }catch(Exception e){
            e.printStackTrace();
        }
    }

}