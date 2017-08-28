package cn.xinyuan.order.service;

import cn.xinyuan.common.util.XinYuanResult;
import cn.xinyuan.order.pojo.vo.OrderInfo;

/**
 * User:josli li
 * Date:17/8/28
 * Time:下午1:50
 * Mail:josli@kargocard.com
 */
public interface OrderService {


    /**
     * 创建订单接口
     * @param orderInfo
     * @return
     */
    XinYuanResult createOrder(OrderInfo orderInfo);

}
