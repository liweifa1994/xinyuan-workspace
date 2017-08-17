package cn.xinyuan.service;

import cn.xinyuan.common.util.EasyUITreeNode;
import cn.xinyuan.common.util.XinYuanResult;
import cn.xinyuan.pojo.TbContentCategory;

import java.util.*;

/**
 * User:josli li
 * Date:17/8/17
 * Time:上午10:02
 * Mail:josli@kargocard.com
 */
public interface ContentCategoryService {

    /**
     * 根据parentId 查询内容分类 只查询一级标题
     * @param parentId
     * @return
     */
    List<EasyUITreeNode> findContentCategoryListByParentId(Long parentId);

    /**
     * 更新或者插入内容分类信息，通过ContentCategory的id 来区分是否是修改或者更新（如果 插入 id 为零 ，否则 反之）
     * @param contentCategory
     * @return
     */
    XinYuanResult insertOrUpdateContentCategory(TbContentCategory contentCategory);
    /**
     * 根据id删除 ContentCategoryService
     * @param id
     * @return
     */
    XinYuanResult deleteCategoryListid(Long id);

}
