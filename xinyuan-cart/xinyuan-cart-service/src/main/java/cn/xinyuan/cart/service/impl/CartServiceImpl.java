package cn.xinyuan.cart.service.impl;

import cn.xinyuan.cart.service.CartService;
import cn.xinyuan.common.util.XinYuanResult;
import cn.xinyuan.mapper.TbItemMapper;
import cn.xinyuan.pojo.TbItem;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User:josli li
 * Date:2017/8/27
 * Time:10:09
 * Mail:josli@kargocard.com
 */
@Service
public class CartServiceImpl implements CartService {

    private Logger log = LoggerFactory.getLogger(CartServiceImpl.class);
    @Autowired
    private RedisTemplate<String,TbItem> redisTemplate;

    @Value("#{xinyuan_user_cart_}")
    protected String userCartKey ;

    @Autowired
    private TbItemMapper itemMapper;
    /**
     * 实现逻如下
     *  1、先查询redsi中是否有指定的商品信息
     *  2、如果有，则将商品数量增加
     *  3、如果没有，则查询数据库，然后设置商品的数量，添加到redis中
     *  注意事项：
     *          保存购物车信息使用redis的hash数据结构来保存 用户的ID 为key,value 为商品信息
     * @param userId
     * @param itemId
     * @param num
     * @return
     */
    @Override
    public XinYuanResult addCard(long userId, long itemId, int num) {

        log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+" {param } "+" userId="+userId+" itemId="+itemId+" num"+num);
        //通用校验

        //
        String cartKey = userCartKey+userId;
        //判断是否存在
        Boolean exitCart = redisTemplate.hasKey(cartKey);
        HashOperations<String, Object, Object> cartHash = redisTemplate.opsForHash();
        try {
            TbItem item = null;
            if (exitCart){
                item = (TbItem)cartHash.get(cartKey, itemId + "");
                item.setNum(item.getNum()+num);
            }else {
                item = itemMapper.selectByPrimaryKey(itemId);
                item.setNum(num);
                String itemImage = item.getImage();
                if (StringUtils.isNoneBlank()){
                    //设置一张图片
                    item.setImage(itemImage.split(",")[0]);
                }
            }
            cartHash.put(cartKey,itemId+"",item);
            return XinYuanResult.ok();
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage());
            //系统错误
            return XinYuanResult.build(405,e.getMessage());
        }

    }

    @Override
    public XinYuanResult megerCard(long userId, List<TbItem> itemList) {
        //通用校验

        try {
            //调用添加商品到购物车逻辑
            for (TbItem item:itemList){
                addCard(userId,item.getId(),item.getNum());
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage());
            //系统错误
            return XinYuanResult.build(405,e.getMessage());
        }
        return XinYuanResult.ok();
    }

    @Override
    public List<TbItem> getCartList(long userId) {
        //通用的校验逻辑 TODO
        String cartKey = userCartKey+userId;
        //判断是否存在
        Boolean exitCart = redisTemplate.hasKey(cartKey);
        if (exitCart){
            List<Object> values = redisTemplate.opsForHash().values(cartKey);
            List<TbItem> list = Lists.newArrayList();
            for ( Object temp:values){
                list.add((TbItem)temp);
            }
            return list;
        }
        return null;
    }

    @Override
    public XinYuanResult updateCartNum(long userId, long itemId, int num) {
        //
        String cartKey = userCartKey+userId;
        //判断是否存在
        Boolean exitCart = redisTemplate.hasKey(cartKey);
        HashOperations<String, Object, Object> cartHash = redisTemplate.opsForHash();
        try {
            if (exitCart){
                TbItem item = (TbItem)cartHash.get(cartKey, itemId + "");
                item.setNum(num);
                cartHash.put(cartKey,itemId+"",item);
                return XinYuanResult.ok();
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage());
            //系统错误
            return XinYuanResult.build(405,e.getMessage());
        }
        return null;
    }

    @Override
    public XinYuanResult deleteCartItem(long userId, long itemId) {
        //
        String cartKey = userCartKey+userId;
        //判断是否存在
        HashOperations<String, Object, Object> cartHash = redisTemplate.opsForHash();
        try {
            cartHash.delete(cartKey,itemId+"");
            return XinYuanResult.ok();
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage());
            //系统错误
            return XinYuanResult.build(405,e.getMessage());
        }

    }

    @Override
    public XinYuanResult clearCartItem(long userId) {
        //
        String cartKey = userCartKey+userId;
        //判断是否存在
        Boolean exitCart = redisTemplate.hasKey(cartKey);
        HashOperations<String, Object, Object> cartHash = redisTemplate.opsForHash();

        if (!exitCart){
            return XinYuanResult.build(404,"用户的购物车不存在");
        }else {
            try {
                redisTemplate.delete(cartKey);
                return XinYuanResult.ok();
            }catch (Exception e){
                e.printStackTrace();
                log.error(e.getMessage());
                //系统错误
                return XinYuanResult.build(405,e.getMessage());
            }
        }
    }
}
