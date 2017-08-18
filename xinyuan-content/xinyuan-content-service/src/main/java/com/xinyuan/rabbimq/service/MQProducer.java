package com.xinyuan.rabbimq.service;

/**
 * User:josli li
 * Date:17/8/18
 * Time:下午4:57
 * Mail:josli@kargocard.com
 */
public interface MQProducer {
    /**
     * 发送消息到指定队列
     * @param queueKey
     * @param object
     */
     void sendDataToQueue(String queueKey, Object object);
}
