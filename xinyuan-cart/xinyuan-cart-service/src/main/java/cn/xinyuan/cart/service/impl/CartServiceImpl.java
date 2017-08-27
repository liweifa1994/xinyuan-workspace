package cn.xinyuan.cart.service.impl;

import cn.xinyuan.cart.service.CartService;
import cn.xinyuan.common.util.XinYuanResult;
import cn.xinyuan.pojo.TbItem;

import java.util.List;

/**
 * User:josli li
 * Date:2017/8/27
 * Time:10:09
 * Mail:josli@kargocard.com
 */
public class CartServiceImpl implements CartService {

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
        return null;
    }

    @Override
    public XinYuanResult addCard(long userId, List<TbItem> itemList) {
        return null;
    }

    @Override
    public List<TbItem> getCartList(long userId) {
        return null;
    }

    @Override
    public XinYuanResult updateCartNum(long userId, long itemId, int num) {
        return null;
    }

    @Override
    public XinYuanResult deleteCartItem(long userId, long itemId) {
        return null;
    }

    @Override
    public XinYuanResult clearCartItem(long userId) {
        return null;
    }
}
