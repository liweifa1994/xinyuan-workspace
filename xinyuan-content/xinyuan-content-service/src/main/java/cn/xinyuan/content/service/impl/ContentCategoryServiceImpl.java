package cn.xinyuan.content.service.impl;

import cn.xinyuan.common.util.Constent;
import cn.xinyuan.common.util.EasyUITreeNode;
import cn.xinyuan.common.util.JSONUtils;
import cn.xinyuan.common.util.XinYuanResult;
import cn.xinyuan.common.util.exception.ExceptionFactoryUtil;
import cn.xinyuan.mapper.TbContentCategoryMapper;
import cn.xinyuan.pojo.TbContentCategory;
import cn.xinyuan.pojo.TbContentCategoryExample;
import cn.xinyuan.content.service.ContentCategoryService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User:josli li
 * Date:17/8/17
 * Time:上午10:24
 * Mail:josli@kargocard.com
 */

@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {
    private Logger logger = LoggerFactory.getLogger(ContentCategoryServiceImpl.class);

    @Autowired
    private TbContentCategoryMapper contentCategoryMapper ;

    @Override
    public List<EasyUITreeNode> findContentCategoryListByParentId(Long parentId) {
        logger.info(" findContentCategoryListByParentId { parentId} ---》"+parentId);
        if (parentId == null){
            logger.error("父级的id为空");
            ExceptionFactoryUtil.createDataNullException("父级的id 不能为空");
        }
        TbContentCategoryExample tbContentCategoryExample = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = tbContentCategoryExample.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbContentCategory> tbContentCategoryList = contentCategoryMapper.selectByExample(tbContentCategoryExample);
        if (tbContentCategoryList != null && tbContentCategoryList.size() >0){
            List<EasyUITreeNode>  result = new ArrayList<>();
            for (TbContentCategory tbContentCategory :tbContentCategoryList){
                EasyUITreeNode treeNode = new EasyUITreeNode();
                //判断是否是父亲节点
                treeNode.setState(tbContentCategory.getIsParent()?Constent.closed:Constent.open);
                treeNode.setText(tbContentCategory.getName());
                treeNode.setId(tbContentCategory.getId());
                result.add(treeNode);
            }
            logger.info(" [result] "+JSONUtils.ObjToJson(result));
            return result;
        } else {
            logger.warn("数据在库中不能存在 ");
            ExceptionFactoryUtil.createResultException("数据不存在");
        }
        logger.error("系统出现异常");
        return null;
    }

    @Override
    public XinYuanResult insertOrUpdateContentCategory(TbContentCategory contentCategory) {
        logger.info(" insertOrUpdateContentCategory {param} "+ JSONUtils.ObjToJson(contentCategory));
        if (contentCategory == null || StringUtils.isBlank(contentCategory.getName())){
            ExceptionFactoryUtil.createDataNullException("所需的参数为空 ");
        }
        Long id = contentCategory.getId();
        int updateConunt = 0;
        String resultMsg = "";
        //表示新增数据
        if (id == null){
            Long parentId = contentCategory.getParentId();
            if (parentId == null){
                logger.error("在新添加数据时候父级id 不能为空 必须有值");
                ExceptionFactoryUtil.createDataNullException("在新添加数据时候父级id 不能为空 必须有值");
            }
            TbContentCategory tbContentCategory = contentCategoryMapper.selectByPrimaryKey(parentId);
            if (tbContentCategory != null){
                //更新
                if (!tbContentCategory.getIsParent()){
                    tbContentCategory.setIsParent(true);
                    contentCategoryMapper.updateByPrimaryKeySelective(tbContentCategory);
                }
            }else {
                logger.error("在新添加数据时候父级 不存在 ");
                ExceptionFactoryUtil.createDataNullException("在新添加数据时候父级 不存在 ");
            }
            contentCategory.setIsParent(false);
            contentCategory.setSortOrder(1);
            contentCategory.setStatus(1);
            contentCategory.setCreated(new Date());
            contentCategory.setUpdated(new Date());
            contentCategoryMapper.insert(contentCategory);
            resultMsg = "保存数据成功";
        }else {
            //表示修改数据
            updateConunt = contentCategoryMapper.updateByPrimaryKeySelective(contentCategory);
            if (updateConunt ==0){
                logger.error("更新数据失败，数据有可能不能存在");
                ExceptionFactoryUtil.createDataNullException("更新数据失败，数据可能不能存在");
            }
            resultMsg = "更新数据成功";
        }
        return XinYuanResult.ok(resultMsg);
    }

    @Override
    public XinYuanResult deleteCategoryListid(Long id) {
        logger.info(" deleteCategoryListid { id} ---》"+id);
        if (id == null){
            logger.error("id为空");
            ExceptionFactoryUtil.createDataNullException("id 不能为空");
        }
        int i = contentCategoryMapper.deleteByPrimaryKey(id);
        if (i<0){
            logger.error("删除数据失败，数据有可能不能存在");
            ExceptionFactoryUtil.createDataNullException("要删除数据可能不能存在");
        }
        return XinYuanResult.ok("删除成功");
    }
}
