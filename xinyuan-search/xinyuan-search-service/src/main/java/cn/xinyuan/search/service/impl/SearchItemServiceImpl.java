package cn.xinyuan.search.service.impl;

import cn.xinyuan.common.pojo.SearchItem;
import cn.xinyuan.common.pojo.SearchResult;
import cn.xinyuan.common.util.XinYuanResult;
import cn.xinyuan.common.util.exception.ExceptionFactoryUtil;
import cn.xinyuan.search.dao.SearchSolrDao;
import cn.xinyuan.search.dao.mapper.SearchItemMapperCustomer;
import cn.xinyuan.search.service.SearchItemService;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * User:josli li
 * Date:17/8/24
 * Time:上午9:42
 * Mail:josli@kargocard.com
 */
@Service
public class SearchItemServiceImpl implements SearchItemService {

    protected static final Logger log = LoggerFactory.getLogger(SearchItemServiceImpl.class);

    @Autowired
    private SearchItemMapperCustomer itemMapperCustomer;
    @Autowired
    private SolrServer solrServer;

    @Autowired
    private SearchSolrDao searchSolrDao;
    @Override
    public XinYuanResult imprtItemListToSolr(Long itemId) {

        List<SearchItem> itemList = itemMapperCustomer.getItemList(itemId);
        if (itemList != null && itemList.size()>0){
            try {
                solrServer.addBeans(itemList);
                solrServer.commit();
                log.info("导入索引库成功 导入了  "+ itemList.size()+" 条");
                return XinYuanResult.ok("导入索引库成功");
            }catch (SolrServerException solrServerException){
                log.error("添加到索引库发生错误"+solrServerException.getMessage());
                ExceptionFactoryUtil.createResultException("添加到索引库发送错误");
            }catch (IOException e){
                log.error("添加到索引库发生错误"+e.getMessage());
                ExceptionFactoryUtil.createResultException("添加到索引库发送错误");
            }
        }else{
            log.warn("商品信息不存在");
            ExceptionFactoryUtil.createDataNullException("商品信息不存在");
        }
        return null;
    }

    @Override
    public SearchResult getItemList( String keyWorkds, int start, int rows) {
        SolrQuery query = new SolrQuery();
        query.setQuery(keyWorkds);
        query.setStart(start);
        query.setRows(rows);
        query.setHighlight(true);
        query.setHighlightSimplePre("<em style=\"colr:red\"");
        query.setHighlightSimplePost("</en>");
        query.set("df","item_title");
        SearchResult searchItem = searchSolrDao.getSearchItem(query,"item_title");
        long recordTotal = searchItem.getRecordTotal();
        int pages = (int)recordTotal / rows;
        //多一页数据
        if (recordTotal % rows != 0)
            pages++;
        searchItem.setTotalPages(pages);
        return searchItem;
    }


}
