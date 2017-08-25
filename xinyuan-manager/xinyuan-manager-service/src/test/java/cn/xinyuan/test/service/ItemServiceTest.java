package cn.xinyuan.test.service;

import java.util.List;

import cn.xinyuan.common.util.EasyUITreeNode;
import cn.xinyuan.common.util.exception.DataFromatErrorException;
import cn.xinyuan.common.util.exception.DataNullException;
import cn.xinyuan.common.util.exception.ResultException;
import cn.xinyuan.service.ItemCatService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.xinyuan.pojo.TbItem;
import cn.xinyuan.service.ItemService;

/**
 * 商品测试类
 * @author josli
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:spring/applicationContext-*.xml")
public class ItemServiceTest {
	Logger log = LoggerFactory.getLogger(ItemServiceTest.class);
	@Autowired
	private ItemService item;

	@Autowired
	private ItemCatService itemCat;
	@Test
	public void testDB() {
		log.info("你好吗");
		System.out.println(item.getItemById(536563).getTitle());
		log.info("你好吗");
	}
	
	@Test
	public void testDB2() {
			List<TbItem> items = (List<TbItem>)item.itemList(1, 30).getRows();
		log.info(items.get(0).getTitle());
	}

	@Test
	public void testItemCatList(){
		List<EasyUITreeNode> itemCatList = null;
		try {
			itemCatList = itemCat.findItemCatList(-6l);
		}catch (DataFromatErrorException e){
			e.printStackTrace();
		}catch (DataNullException e){
			e.printStackTrace();
		}catch (ResultException e){
			e.printStackTrace();
		}catch (Exception e){
			e.printStackTrace();
		}

		log.info(itemCatList.toString());
	}


}
