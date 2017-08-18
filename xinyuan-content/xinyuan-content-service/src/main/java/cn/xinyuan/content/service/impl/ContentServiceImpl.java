package cn.xinyuan.content.service.impl;

import cn.xinyuan.common.util.EasyUIDataGridResult;
import cn.xinyuan.common.util.JSONUtils;
import cn.xinyuan.common.util.XinYuanResult;
import cn.xinyuan.common.util.exception.ExceptionFactoryUtil;
import cn.xinyuan.mapper.TbContentMapper;
import cn.xinyuan.pojo.TbContent;
import cn.xinyuan.pojo.TbContentExample;
import cn.xinyuan.content.service.ContentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


/**
 * User:josli li
 * Date:17/8/17
 * Time:下午12:10
 * Mail:josli@kargocard.com
 */

@Service
public class ContentServiceImpl implements ContentService {

    private Logger logger = LoggerFactory.getLogger(ContentServiceImpl.class);

    @Autowired
    private TbContentMapper contentMapper;

    @Value("CONTENT_LIST")
    private String contentListKey;

    @Autowired
    private RedisTemplate<String,List<TbContent>> redisTemplate;

    @Override
    public EasyUIDataGridResult findContentList(Long contentCategoryId, int page, int rows) {
        logger.info("findContentList {param}" + "contentCategoryId = " + contentCategoryId + " page=" + page + " rows=" + rows);
        if (contentCategoryId == null || page < 0 || rows <= 0) {
            ExceptionFactoryUtil.createDataFromatErrorException("输入的参数有误");
        }
        PageHelper.startPage(page, rows);
        List<TbContent> tbContentList =getList(contentCategoryId);
        if (tbContentList != null && tbContentList.size()>0){
            //取分页信息
            PageInfo<TbContent> pageInfo = new PageInfo<>(tbContentList);
            EasyUIDataGridResult result = new EasyUIDataGridResult();
            result.setTotal(pageInfo.getSize());
            result.setRows(pageInfo.getList());
            logger.info("findContentList {result} "+ JSONUtils.ObjToJson(result));
            return result;
        }else{
            logger.error("返回结果为空");
            ExceptionFactoryUtil.createResultException("返回结果为空");
        }
        return null;
    }

    /**
     *
     * 执行查询
     * @param contentCategoryId
     * @return
     */
    private List<TbContent> getList(Long contentCategoryId){
         TbContentExample tbContentExample = new TbContentExample();
         TbContentExample.Criteria criteria = tbContentExample.createCriteria();
         criteria.andCategoryIdEqualTo(contentCategoryId);
        return contentMapper.selectByExample(tbContentExample);
    }
    @Override
    public List<TbContent> findContentList(Long contentCategoryId) {
        //打印入口参数
        logger.info("findContentList {param}" + "contentCategoryId = " + contentCategoryId );
        if (contentCategoryId == null) {
            ExceptionFactoryUtil.createDataFromatErrorException("输入的参数有误");
        }
        //从redis中查询数据 如果查询到 直接返回数据
        HashOperations<String, String, List<TbContent>> contentForHash = redisTemplate.opsForHash();
        List<TbContent> contentRedisList = contentForHash.get(contentListKey, contentCategoryId + "");
        if (contentRedisList != null && contentRedisList.size()>0){
            logger.info("findContentList {result} in redis " + JSONUtils.ObjToJson(contentRedisList) );
            return contentRedisList;
        }
        //从数据库中查询数据
        List<TbContent> tbContentList = getList(contentCategoryId);
        if (tbContentList != null && tbContentList.size()>0){
            logger.info("findContentList {result} in redis " + "contentCategoryId = " + JSONUtils.ObjToJson(contentRedisList) );
            //缓存数据 到redis
            contentForHash.put(contentListKey,contentCategoryId + "",tbContentList);
            return tbContentList;
        }else {
            logger.error("返回结果为空");
            ExceptionFactoryUtil.createResultException("返回结果为空");
        }
        return null;
    }

    @Override
    public XinYuanResult insertOrUpdateContent(TbContent content) {
        logger.info(" insertOrUpdateContent {param} "+ JSONUtils.ObjToJson(content));
        if (content == null ) {
            ExceptionFactoryUtil.createDataFromatErrorException("输入的参数有误");
        }
        Long id = content.getId();
        String resutlMsg = "";
        try {
            if (id == null){
                //表示新增数据
                content.setCreated(new Date());
                content.setUpdated(new Date());
                contentMapper.insertSelective(content);
                resutlMsg = "新增数据成功";
            }else {
                //表示修改数据
                content.setUpdated(new Date());
                contentMapper.updateByPrimaryKeySelective(content);
                resutlMsg = "修改数据成功";
            }
        } catch (Exception e){
            resutlMsg = e.getMessage();
            logger.error(resutlMsg);
            ExceptionFactoryUtil.createResultException(resutlMsg);
        }

        return XinYuanResult.ok(resutlMsg);
    }

    @Override
    public XinYuanResult deleteContent(Long contentId) {
        logger.info(" deleteContent {param} "+ JSONUtils.ObjToJson(contentId));
        if (contentId == null ) {
            ExceptionFactoryUtil.createDataFromatErrorException("输入的参数有误");
        }
        try {
            int i = contentMapper.deleteByPrimaryKey(contentId);
            if (i<=0){
                ExceptionFactoryUtil.createResultException("删除数据失败,数据可能不存在");
            }
            logger.info(" exec result   "+ JSONUtils.ObjToJson(i));
            return XinYuanResult.ok("删除数据成功");
        }catch (Exception e){
            e.printStackTrace();
            ExceptionFactoryUtil.createResultException(e.getMessage());
        }
        return null;
    }
}
