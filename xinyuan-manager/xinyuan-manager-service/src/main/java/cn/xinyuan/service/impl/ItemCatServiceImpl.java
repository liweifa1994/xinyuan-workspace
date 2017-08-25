package cn.xinyuan.service.impl;

import cn.xinyuan.common.util.Constent;
import cn.xinyuan.common.util.EasyUITreeNode;
import cn.xinyuan.common.util.exception.ExceptionFactoryUtil;
import cn.xinyuan.service.ItemCatService;
import cn.xinyuan.mapper.TbItemCatMapper;
import cn.xinyuan.pojo.TbItemCat;
import cn.xinyuan.pojo.TbItemCatExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * User:josli li
 * Date:17/8/15
 * Time:下午3:25
 * Mail:josli@kargocard.com
 */
@Service
public class ItemCatServiceImpl implements ItemCatService {
    private Logger logger = LoggerFactory.getLogger(ItemCatServiceImpl.class);
    @Autowired
    private TbItemCatMapper itemCatMapper;
    @Override
    public List<EasyUITreeNode> findItemCatList(Long parentId) {
        logger.info("{ param  parentId }"+parentId);
        if (parentId == null){
            logger.error("父级的id为空");
            ExceptionFactoryUtil.createDataNullException("父级的id 不能为空");
        }
        TbItemCatExample itemCatExample = new TbItemCatExample();
        TbItemCatExample.Criteria criteria = itemCatExample.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbItemCat> tbItemCatList = itemCatMapper.selectByExample(itemCatExample);
        if (tbItemCatList != null && tbItemCatList.size()>0){
            List<EasyUITreeNode>  result = new ArrayList<>();
            for (TbItemCat itemCat :tbItemCatList){
                EasyUITreeNode treeNode = new EasyUITreeNode();
                //判断是否是父亲节点
                treeNode.setState(itemCat.getIsParent()?Constent.closed:Constent.open);
                treeNode.setText(itemCat.getName());
                treeNode.setId(itemCat.getId());
                result.add(treeNode);
            }
            //
            logger.info(result.toString());
            return result;
        }else{
            logger.warn("数据在库中不能存在 ");
            ExceptionFactoryUtil.createResultException("数据不存在");
        }
        return null;
    }
}
