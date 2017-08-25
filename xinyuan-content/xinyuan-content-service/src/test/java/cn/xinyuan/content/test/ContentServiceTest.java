package cn.xinyuan.content.test;

import cn.xinyuan.common.util.JSONUtils;
import cn.xinyuan.pojo.TbContent;
import cn.xinyuan.service.ContentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * User:josli li
 * Date:17/8/17
 * Time:上午11:18
 * Mail:josli@kargocard.com
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:spring/applicationContext-*.xml")
public class ContentServiceTest {
    private Logger logger = LoggerFactory.getLogger(ContentServiceTest.class);
    @Autowired
    private ContentService contentService;

    @Test
    public void testselectContentCategory(){
        logger.info("begin ");
        logger.info(JSONUtils.ObjToJson(contentService.findContentList(89l)));
        logger.info(JSONUtils.ObjToJson(contentService.findContentList(89l).size()));
        logger.info("end");
    }
    @Test
    public void testInsert(){
        TbContent tbContent = new TbContent();
        tbContent.setTitle("hello world");
        tbContent.setCategoryId(89l);
        logger.info("begin ");
        logger.info(JSONUtils.ObjToJson(contentService.insertOrUpdateContent(tbContent)));
        logger.info("end");
    }

    @Test
    public void testUpdate(){
        TbContent tbContent = new TbContent();
        tbContent.setId(32l);
        tbContent.setTitle("hello world 12321");
        tbContent.setTitleDesc("hello world 12321");
        logger.info("begin ");
        logger.info(JSONUtils.ObjToJson(contentService.insertOrUpdateContent(tbContent)));
        logger.info("end");
    }

    @Test
    public void deleteContent(){
        logger.info("begin ");
        logger.info(JSONUtils.ObjToJson(contentService.deleteContent(33l)));
        logger.info("end");
    }

}
