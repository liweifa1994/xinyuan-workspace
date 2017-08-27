package cn.xinyuan.service.impl;

import cn.xinyuan.common.util.EasyUIDataGridResult;
import cn.xinyuan.common.util.XinYuanResult;
import cn.xinyuan.common.util.exception.ExceptionFactoryUtil;
import cn.xinyuan.mapper.TbItemDescMapper;
import cn.xinyuan.mapper.TbItemMapper;
import cn.xinyuan.pojo.TbItem;
import cn.xinyuan.pojo.TbItemDesc;
import cn.xinyuan.pojo.TbItemExample;
import cn.xinyuan.service.ItemService;
import cn.xinyuan.service.SequenceService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 商品管理service
 */
@Service
public class ItemServiceImpl implements ItemService {
	private Logger log = LoggerFactory.getLogger(ItemServiceImpl.class);
	@Autowired
	private TbItemMapper itemMapper;

	@Autowired
	private TbItemDescMapper itemDescMapper;

	@Autowired
	private SequenceService sequenceService ;

	@Resource
	private AmqpTemplate searchItemSolrRabbitmq;

	@Value("xinyuan_redis_item_key")
	protected String redisItemInfoKey;

	@Value("item_info_pre")
	protected String itemInfo;

	@Value("item_desc_pre")
	protected String itemDesc;

	@Value("item_time_cache_expire")
	protected long itemCahceTime;

	@Value("rabbitmq_queue_solr")
	protected String rabbitmqQueue;

	@Autowired
	private RedisTemplate<String,Object> redisTemplate;

	/**
	 * 实现逻辑如下
	 * 1、先去缓存系统中查询是否由该商品的信息 如果有直接返回
	 * 2、如果缓存系统中没有该商品信息，则直接查询数据
	 * 3、查询到商品信息之后，将该商品添加到Redis缓存系统中 并且设置过期时间
	 * @param itemId
	 * @return
	 */
	@Override
	public TbItem getItemById(long itemId) {
		//通用的校验

		//1、先去缓存系统中查询是否由该商品的信息 如果有直接返回
		ValueOperations<String, Object> opsForValue = redisTemplate.opsForValue();
		String key = redisItemInfoKey+itemId+":"+itemInfo;
		TbItem tbItem = (TbItem) opsForValue.get(key);
		if(tbItem != null){
			return tbItem;
		}
		//2、如果缓存系统中没有该商品信息，则直接查询数据
		tbItem = itemMapper.selectByPrimaryKey(itemId);
		//3、查询到商品信息之后，将该商品添加到Redis缓存系统中 并且设置过期时间
		if(tbItem != null){
			opsForValue.set(key,tbItem);
			//设置过期时间
			redisTemplate.expire(key,itemCahceTime,TimeUnit.SECONDS);
			return tbItem;
		}
		return null;
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

	/**
	 * 实现逻辑如下
	 * 1、先去缓存系统中查询是否由该商品的描述信息 如果有直接返回
	 * 2、如果缓存系统中没有该商品描述信息，则直接查询数据库
	 * 3、查询到商品描述信息之后，将该商品描述信息添加到Redis缓存系统中 并且设置过期时间
	 * @param itemId
	 * @return
	 */
	@Override
	public TbItemDesc getItemDescById(long itemId) {
		//通用的校验

		//1、先去缓存系统中查询是否由该商品的信息 如果有直接返回
		ValueOperations<String, Object> opsForValue = redisTemplate.opsForValue();
		String key = redisItemInfoKey+itemId+":"+itemDesc;
		TbItemDesc tbItemDesc = null;
		try {
			tbItemDesc = (TbItemDesc) opsForValue.get(key);
			if(tbItemDesc != null){
				return tbItemDesc;
			}
		}catch (Exception e){
			e.printStackTrace();
			log.error(e.getLocalizedMessage());
			ExceptionFactoryUtil.createResultException("从redis中查询数据失败");
		}
		//2、如果缓存系统中没有该商品信息，则直接查询数据
		tbItemDesc = itemDescMapper.selectByPrimaryKey(itemId);
		//3、查询到商品信息之后，将该商品添加到Redis缓存系统中 并且设置过期时间
		if(tbItemDesc != null){
			opsForValue.set(key,tbItemDesc);
			//设置过期时间
			redisTemplate.expire(key,itemCahceTime,TimeUnit.SECONDS);
			return tbItemDesc;
		}
		return null;
	}

	@Override
	public XinYuanResult addItem(TbItem item, String desc) {
		//
		SimpleDateFormat dateFormat = new SimpleDateFormat("YYYYMMddHHmmss");
		String  itemIdString= dateFormat.format(new Date()) + sequenceService.getNextSeqId("tbitem", 1);
		long itemId = Long.parseLong(itemIdString);
		item.setId(itemId);
		//1 正常 2 下架 3 删除
		item.setStatus((byte)1);
		item.setCreated(new Date());
		item.setUpdated(new Date());
		//添加商品
		itemMapper.insert(item);
		//添加商品描述信息
		TbItemDesc itemDesc = new TbItemDesc();
		itemDesc.setUpdated(new Date());
		itemDesc.setCreated(new Date());
		itemDesc.setItemDesc(desc);
		itemDesc.setItemId(itemId);
		itemDescMapper.insert(itemDesc);
		//发送消息给队列 让商品信息导入到索引库中去
		searchItemSolrRabbitmq.convertAndSend(rabbitmqQueue,item);
		return XinYuanResult.ok();
	}


}
