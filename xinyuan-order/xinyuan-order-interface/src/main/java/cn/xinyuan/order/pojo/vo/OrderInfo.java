package cn.xinyuan.order.pojo.vo;

import cn.xinyuan.pojo.TbOrder;
import cn.xinyuan.pojo.TbOrderItem;
import cn.xinyuan.pojo.TbOrderShipping;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
/**
 * User:josli li
 * Date:17/8/28
 * Time:下午1:44
 * Mail:josli@kargocard.com
 */
@Getter
@Setter
public class OrderInfo extends TbOrder {

    //商品列表
    private List<TbOrderItem> orderItems;
    //订单信息 如收货地址
    private TbOrderShipping orderShipping;

}
