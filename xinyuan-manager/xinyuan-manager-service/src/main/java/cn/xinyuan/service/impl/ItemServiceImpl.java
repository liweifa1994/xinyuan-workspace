package cn.xinyuan.service.impl;

import java.util.List;

import cn.xinyuan.common.util.XinYuanResult;
import cn.xinyuan.service.ItemService;
import cn.xinyuan.service.SequenceService;
import org.springframework.beans.factory.annotation.Autowired;
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
	private SequenceService sequenceService ;

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

		return null;
	}


}
