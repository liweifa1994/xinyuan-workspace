package cn.xinyuan.search.dao;

import cn.xinyuan.common.pojo.SearchItem;
import cn.xinyuan.common.pojo.SearchResult;
import cn.xinyuan.common.util.exception.ExceptionFactoryUtil;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * User:josli li
 * Date:17/8/24
 * Time:下午3:23
 * Mail:josli@kargocard.com
 */
@Repository
public class SearchSolrDao {
    private Logger log = LoggerFactory.getLogger(SearchSolrDao.class);
    @Autowired
    private SolrServer solrServer;
    public SearchResult getSearchItem(SolrQuery query, String highitKey){
        try {
            QueryResponse queryResponse = solrServer.query(query);
            SearchResult searchResult = new SearchResult();
            searchResult.setRecordTotal(queryResponse.getResults().getNumFound());
            //获取商品列表
            List<SearchItem> searchItemList = queryResponse.getBeans(SearchItem.class);
            if (null != highitKey && highitKey.length()>0){
                Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
                for (SearchItem item : searchItemList){
                    List<String> itemTitle = highlighting.get(item.getId()).get(highitKey);
                    //如果存在高亮的字 则获取高亮的字
                    if (itemTitle != null && itemTitle.size() >0){
                        item.setItem_title(itemTitle.get(0));
                    }
                }
            }
            searchResult.setItemList(searchItemList);
            return searchResult;
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage());
            ExceptionFactoryUtil.createResultException("查询solr数据库出错了");
        }

        return null;
    }

}
