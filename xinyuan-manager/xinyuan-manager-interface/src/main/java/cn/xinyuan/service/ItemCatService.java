package cn.xinyuan.service;

import cn.xinyuan.common.util.EasyUITreeNode;

import java.util.*;

/**
 * User:josli li
 * Date:17/8/15
 * Time:下午3:13
 * Mail:josli@kargocard.com
 */
public interface ItemCatService {

    /**
     * 根据父节点获取子类目
     * @param parentId
     * @return
     */
    List<EasyUITreeNode> findItemCatList(Long parentId);
}
