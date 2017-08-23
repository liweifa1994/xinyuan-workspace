package cn.xinyuan.search.test.solr;

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

    private Logger log = LoggerFactory.getLogger(SolrServiceTest.class);
    @Test
    public void testSolrAdd(){
        SolrInputDocument document = new SolrInputDocument();
        // 第四步：向文档中添加域。必须有id域，域的名称必须在schema.xml中定义。
        document.addField("id", "test001");
        document.addField("item_title", "测试商品");
        document.addField("item_price", "199");
        try {
            UpdateResponse response = solrServer.add(document);
            solrServer.commit();
            if (response.getStatus()== 0) {
                log.info("solr testSolrAdd success:" + response.getStatus());
            }
            solrServer.commit();

        }catch (Exception e){
            e.printStackTrace();

        }
    }


    @Test
    public void testSolrDelete() throws Exception{
        UpdateResponse response = solrServer.deleteById("test001");
        log.info(response.toString());
        if (response.getStatus()== 0) {
            log.info("solr testSolrDelete success:" + response.getStatus());
        }
        solrServer.commit();
    }


    @Test
    public void testSolrueryQ()throws Exception{
        SolrQuery solrQuery = new SolrQuery();
        //设置条件 不推荐使用下面的方式
        solrQuery.setQuery("id:test001");
        //设置要查询的字段
        solrQuery.setFields("item_title,item_price");
        //返回 结果集
        QueryResponse queryResponse = solrServer.query(solrQuery);
        queryResponse.getStatus();
        SolrDocumentList results = queryResponse.getResults();

        for (SolrDocument document :results){
            System.out.println(document.get("id"));
            System.out.println(document.get("item_title"));
            System.out.println(document.get("item_price"));
        }
    }
}
