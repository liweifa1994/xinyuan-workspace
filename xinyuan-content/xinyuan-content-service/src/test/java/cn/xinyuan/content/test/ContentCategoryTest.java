package cn.xinyuan.content.test;

import cn.xinyuan.common.util.JSONUtils;
import cn.xinyuan.pojo.TbContentCategory;
import cn.xinyuan.service.ContentCategoryService;
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
public class ContentCategoryTest {
    private Logger logger = LoggerFactory.getLogger(ContentCategoryTest.class);
    @Autowired
    private ContentCategoryService contentCategoryService;
    @Test
    public void testselectContentCategory(){
        logger.info("begin ");
        logger.info(JSONUtils.ObjToJson(contentCategoryService.findContentCategoryListByParentId(87l)));
        logger.info("end");
    }
    @Test
    public void testInsert(){
        TbContentCategory contentCategory = new TbContentCategory();
        contentCategory.setParentId(86l);
        contentCategory.setName("kargo商城");
        logger.info("begin ");
        logger.info(JSONUtils.ObjToJson(contentCategoryService.insertOrUpdateContentCategory(contentCategory)));
        logger.info("end");
    }

    @Test
    public void testUpdate(){
        TbContentCategory contentCategory = new TbContentCategory();
        contentCategory.setId(99l);
        contentCategory.setName("kargo商城123456");
        logger.info("begin ");
        logger.info(JSONUtils.ObjToJson(contentCategoryService.insertOrUpdateContentCategory(contentCategory)));
        logger.info("end");
    }

    @Test
    public void testdelete(){
        logger.info("begin ");
        logger.info(JSONUtils.ObjToJson(contentCategoryService.deleteCategoryListid(99l)));
        logger.info("end");
    }

}
