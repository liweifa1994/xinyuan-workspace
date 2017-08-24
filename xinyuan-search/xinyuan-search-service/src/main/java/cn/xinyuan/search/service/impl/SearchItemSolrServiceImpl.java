package cn.xinyuan.search.service.impl;

import cn.xinyuan.search.service.SearchItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * User:josli li
 * Date:17/8/24
 * Time:下午4:57
 * Mail:josli@kargocard.com
 */
@Component
public class SearchItemSolrServiceImpl implements MessageListener {

    protected static final Logger logger = LoggerFactory.getLogger(SearchItemSolrServiceImpl.class);

    @Autowired
    private SearchItemService searchItemService;
    //监听消息
    @Override
    public void onMessage(Message message) {
        String str = new String(message.getBody());
        logger.info(" ----------->"+str);
        try {
            Long itemId = Long.parseLong(str);
            searchItemService.imprtItemListToSolr(itemId);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            return;
        }

    }
}
