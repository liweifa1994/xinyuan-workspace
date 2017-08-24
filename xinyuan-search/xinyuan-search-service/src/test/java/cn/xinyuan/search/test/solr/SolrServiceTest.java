package cn.xinyuan.search.test.solr;

import cn.xinyuan.common.pojo.SearchItem;
import cn.xinyuan.common.pojo.SearchResult;
import cn.xinyuan.common.util.JSONUtils;
import cn.xinyuan.search.dao.SearchSolrDao;
import cn.xinyuan.search.service.SearchItemService;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Map;

/**
 * User:josli li
 * Date:17/8/23
 * Time:下午5:04
 * Mail:josli@kargocard.com
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:spring/applicationContext-*.xml")
public class SolrServiceTest {
    @Autowired
    private SolrServer solrServer;

    @Autowired
    private SearchItemService searchItemService;

//    @Autowired
//    private SearchSolrDao searchSolrDao;
    private Logger log = LoggerFactory.getLogger(SolrServiceTest.class);
//    @Test
//    public void testSolrAdd(){
//        SolrInputDocument document = new SolrInputDocument();
//        // 第四步：向文档中添加域。必须有id域，域的名称必须在schema.xml中定义。
//        document.addField("id", "test001");
//        document.addField("item_title", "测试商品");
//        document.addField("item_price", "199");
//        try {
//            UpdateResponse response = solrServer.add(document);
//            solrServer.commit();
//            if (response.getStatus()== 0) {
//                log.info("solr testSolrAdd success:" + response.getStatus());
//            }
//            solrServer.commit();
//
//        }catch (Exception e){
//            e.printStackTrace();
//
//        }
//    }
//
//
//    @Test
//    public void testSolrDelete() throws Exception{
//        UpdateResponse response = solrServer.deleteById("test001");
//        log.info(response.toString());
//        if (response.getStatus()== 0) {
//            log.info("solr testSolrDelete success:" + response.getStatus());
//        }
//        solrServer.commit();
//    }
//
//
    @Test
    public void testSolrueryQ()throws Exception{
        SearchResult result = searchItemService.getItemList("双卡双待",0,10);
        System.out.println(result.getRecordTotal());
        System.out.println(JSONUtils.ObjToJson(result.getItemList()));
        System.out.println(JSONUtils.ObjToJson(result.getItemList().size()));
    }

    @Test
    public void testImport(){
        try {
            searchItemService.imprtItemListToSolr();
        }catch (Exception e){
            e.printStackTrace();

        }
    }


//    public SearchResult getSearchItem(SolrQuery query,String highitKey){
//        try {
//            QueryResponse queryResponse = solrServer.query(query);
//            SearchResult searchResult = new SearchResult();
//            searchResult.setRecordTotal(queryResponse.getResults().getNumFound());
//            List<SearchItem> searchItemList = queryResponse.getBeans(SearchItem.class);
//            if (null != highitKey && highitKey.length()>0){
//                Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
//                for (SearchItem item : searchItemList){
//                    List<String> itemTitle = highlighting.get(item.getId()).get(highitKey);
//                    //如果存在高亮的字 则获取高亮的字
//                    if (itemTitle != null && itemTitle.size() >0){
//                        item.setItem_title(itemTitle.get(0));
//                    }
//                }
//            }
//            searchResult.setItemList(searchItemList);
//           return searchResult;
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//        return null;
//    }
}
