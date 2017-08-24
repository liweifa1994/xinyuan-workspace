package cn.xinyuan.search.service;

import cn.xinyuan.common.pojo.SearchResult;
import cn.xinyuan.common.util.XinYuanResult;

/**
 * User:josli li
 * Date:17/8/24
 * Time:上午9:08
 * Mail:josli@kargocard.com
 */
public interface SearchItemService {

    /**
     * 导入一条数据或全部数据到索引库中
     * @param itemId
     * @return
     */
    XinYuanResult imprtItemListToSolr(Long itemId);

    /**
     * 从solr中获取数据
     * @param keyWorkds 查询域的值
     * @param start 从第几条开始显示数据
     * @param rows 每页显示几条数据
     * @return
     */
    SearchResult getItemList(String keyWorkds,int start,int rows);


}
