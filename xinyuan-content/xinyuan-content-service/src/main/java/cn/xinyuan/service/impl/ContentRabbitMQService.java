package cn.xinyuan.service.impl;

import cn.xinyuan.common.util.JSONUtils;
import cn.xinyuan.service.ContentService;
import cn.xinyuan.pojo.TbContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by josli on 2017/8/20.
 */
@Component
public class ContentRabbitMQService implements MessageListener {
    private Logger logger = LoggerFactory.getLogger(ContentRabbitMQService.class);

    @Autowired
    private ContentService contentService;
    @Value("#{contentConfig.CONTENT_LIST}")
    private String contentListKey;

    @Autowired
    private RedisTemplate<String,List<TbContent>> redisTemplate;

    public void onMessage(Message message) {
       String str = new String(message.getBody());
       try {
           Long categoryId = Long.parseLong(str);
           //从数据库中查询数据
           List<TbContent> tbContentList = contentService.getList(categoryId);
           if (tbContentList != null && tbContentList.size()>0){
               HashOperations<String, String, List<TbContent>> contentForHash = redisTemplate.opsForHash();
               logger.info("ContentRabbitMQService onMessage {result} in redis " + "contentCategoryId = " + JSONUtils.ObjToJson(tbContentList) );
               //缓存数据 到redis
               logger.info("ContentRabbitMQService in redis hhash key-key"+contentListKey +"--->"+categoryId.intValue());
               contentForHash.put(contentListKey, categoryId.intValue()+ "",tbContentList);
           }else {
               logger.error("返回结果为空");
               //  ExceptionFactoryUtil.createResultException("返回结果为空");
           }
       }catch (Exception e){
           e.printStackTrace();
           return;
       }

    }
}
