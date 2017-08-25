package cn.xinyuan.service;

import cn.xinyuan.common.util.EasyUIDataGridResult;
import cn.xinyuan.common.util.XinYuanResult;
import cn.xinyuan.pojo.TbItem;

public interface ItemService {
	
	/**
	 * 根据商品的ID返回商品信息
	 * @param itemId
	 * @return
	 */
	TbItem getItemById(long itemId );
	
	/**
	 * 获取商品列表
	 * @param page
	 * @param rows
	 * @return
	 */
	EasyUIDataGridResult itemList(int page,int rows);

	/**
	 * 添加商品信息
	 * @param item
	 * @param desc
	 * @return
	 */
	XinYuanResult addItem(TbItem item, String desc);
}