package com.xinyuan.rabbimq.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * User:josli li
 * Date:17/8/18
 * Time:下午4:58
 * Mail:josli@kargocard.com
 */
@Component
public class MQProducerImpl implements MQProducer {
    @Autowired
    private AmqpTemplate amqpTemplate;

    private final static Logger log = LoggerFactory.getLogger(MQProducerImpl.class);

    @Override
    public void sendDataToQueue(String queueKey, Object object) {
        try {
            amqpTemplate.convertAndSend(queueKey, object);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

    }
}