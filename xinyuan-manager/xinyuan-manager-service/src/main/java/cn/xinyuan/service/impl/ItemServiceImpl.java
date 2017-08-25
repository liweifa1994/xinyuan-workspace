package cn.xinyuan.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.xinyuan.common.util.XinYuanResult;
import cn.xinyuan.mapper.TbItemDescMapper;
import cn.xinyuan.pojo.TbItemDesc;
import cn.xinyuan.service.ItemService;
import cn.xinyuan.service.SequenceService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.xinyuan.common.util.EasyUIDataGridResult;
import cn.xinyuan.mapper.TbItemMapper;
import cn.xinyuan.pojo.TbItem;
import cn.xinyuan.pojo.TbItemExample;

/**
 * 商品管理service
 */
@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private TbItemMapper itemMapper;

	@Autowired
	private TbItemDescMapper itemDescMapper;
	@Autowired
	private SequenceService sequenceService ;

	@Autowired
	@Qualifier("searchItemSolrRabbitmq")
	private AmqpTemplate searchItemSolrRabbitmq;

	@Override
	public TbItem getItemById(long itemId) {
		return itemMapper.selectByPrimaryKey(itemId);
	}

	@Override
	public EasyUIDataGridResult itemList(int page, int rows) {
		// TODO Auto-generated method stub
		PageHelper.startPage(page, rows);
		//查询数据库
		// 1.1 生成 tbitemexample
		//1.2 使用itemmapper查询
		TbItemExample itemExample = new TbItemExample();
		List<TbItem> list = itemMapper.selectByExample(itemExample);
		//取分页信息
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setTotal(pageInfo.getSize());
		result.setRows(pageInfo.getList());
		return result;
	}

	@Override
	public XinYuanResult addItem(TbItem item, String desc) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("YYYYMMddHHmmss");
		String  itemIdString= dateFormat.format(new Date()) + sequenceService.getNextSeqId("tbitem", 1);
		long itemId = Long.parseLong(itemIdString);
		item.setId(itemId);
		//1 正常  2 下架 3 删除
		item.setStatus((byte)1);
		item.setCreated(new Date());
		item.setUpdated(new Date());
		//添加商品
		itemMapper.insert(item);
		//
		TbItemDesc itemDesc = new TbItemDesc();
		itemDesc.setUpdated(new Date());
		itemDesc.setCreated(new Date());
		itemDesc.setItemDesc(desc);
		itemDesc.setItemId(itemId);
		itemDescMapper.insert(itemDesc);
		//发送消息给队列
		searchItemSolrRabbitmq.convertAndSend("xinyuan.item.info",itemId);

		return XinYuanResult.ok();
	}


}
