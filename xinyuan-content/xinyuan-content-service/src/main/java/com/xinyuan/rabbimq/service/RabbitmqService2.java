package com.xinyuan.rabbimq.service;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Created by josli on 2017/8/20.
 */

@Component
public class RabbitmqService2 {

    @RabbitListener(queues = "xinyuan.content.queue.tag.v1")
    public void hello(String id,Message msg) {
        System.out.println("Received request for id " + id);
    }
}
