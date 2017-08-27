package cn.xinyuan.cart.service;

import cn.xinyuan.common.util.XinYuanResult;
import cn.xinyuan.pojo.TbItem;

import java.util.List;
/**
 * User:josli li
 * Date:2017/8/27
 * Time:10:00
 * Mail:josli@kargocard.com
 */
public interface CartService {

    /**
     * 添加商品到购物车
     * @param userId
     * @param itemId
     * @param num
     * @return
     */
    XinYuanResult addCard(long userId,long itemId,int num);

    /**
     * 添加商品列表到购物车
     * @param userId
     * @param itemList
     * @return
     */
    XinYuanResult addCard(long userId, List<TbItem> itemList);

    /**
     * 获取购物车列表
     * @param userId
     * @return
     */
    List<TbItem> getCartList(long userId);

    /**
     * 更新购物车商品数量
     * @param userId
     * @param itemId
     * @param num
     * @return
     */
    XinYuanResult updateCartNum(long userId, long itemId, int num);

    /**
     * 删除指定的商品
     * @param userId
     * @param itemId
     * @return
     */
    XinYuanResult deleteCartItem(long userId, long itemId);

    /**
     * 清空购物车
     * @param userId
     * @return
     */
    XinYuanResult clearCartItem(long userId);
}
