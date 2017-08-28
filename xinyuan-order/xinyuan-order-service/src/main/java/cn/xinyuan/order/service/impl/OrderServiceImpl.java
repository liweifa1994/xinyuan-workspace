package cn.xinyuan.order.service.impl;

import cn.xinyuan.common.util.XinYuanResult;
import cn.xinyuan.mapper.TbOrderItemMapper;
import cn.xinyuan.mapper.TbOrderMapper;
import cn.xinyuan.mapper.TbOrderShippingMapper;
import cn.xinyuan.order.pojo.vo.OrderInfo;
import cn.xinyuan.order.service.OrderService;
import cn.xinyuan.pojo.TbOrderItem;
import cn.xinyuan.pojo.TbOrderShipping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * User:josli li
 * Date:17/8/28
 * Time:下午1:53
 * Mail:josli@kargocard.com
 */
@Service
public class OrderServiceImpl implements OrderService{

    private Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired

    private TbOrderMapper orderMapper;

    @Autowired

    private TbOrderItemMapper orderItemMapper;

    @Autowired

    private TbOrderShippingMapper orderShippingMapper;

    @Autowired

    private RedisTemplate<String,String> redisTemplate;

    @Value("#{orderIdStart}")
    private String orderIdStart;

    @Value("#{orderItemIdStart}")
    private String orderItemIdStart;

    @Value("#{orderIdKey}")
    private String orderIdKey;

    @Value("#{orderItemIdKey}")
    private String orderItemIdKey;

    /**
     * 实现逻辑如下
     *  1 使用redis生成订单号和订单明细
     *  2 向订单表插入一条订单信息
     *  3 向订单明细表插入多条订单明细 每一笔订单明细的订单id为订单号的id
     *  4 向物流表中插入物流信息 设置订单号为这笔订单号的id
     *  5
     * @param orderInfo
     * @return
     */
    @Override
    public XinYuanResult createOrder(OrderInfo orderInfo) {
        //通用校验 TODO
        //不存在设置初始值

        try {
            if (!redisTemplate.hasKey(orderIdKey)){
                redisTemplate.opsForValue().set(orderIdKey,orderIdStart);
            }
            String orderIdString = redisTemplate.opsForValue().increment(orderIdKey,1).toString();
            orderInfo.setOrderId(orderIdString);
            orderInfo.setPostFee("0");
            //1、未付款，2、已付款，3、未发货，4、已发货，5、交易成功，6、交易关闭
            orderInfo.setStatus(1);
            Date date = new Date();
            orderInfo.setCreateTime(date);
            orderInfo.setUpdateTime(date);
            orderMapper.insertSelective(orderInfo);
            for (TbOrderItem orderItem:orderInfo.getOrderItems()){
                if (!redisTemplate.hasKey(orderItemIdKey)){
                    redisTemplate.opsForValue().set(orderItemIdKey,orderItemIdStart);
                }
                String orderItemIdString = redisTemplate.opsForValue().increment(orderItemIdKey,1).toString();
                orderItem.setOrderId(orderIdString);
                orderItem.setItemId(orderItemIdString);
                orderItemMapper.insertSelective(orderItem);
            }
            TbOrderShipping orderShipping = orderInfo.getOrderShipping();
            orderShipping.setOrderId(orderIdString);
            orderShipping.setCreated(date);
            orderShipping.setUpdated(date);
            orderShippingMapper.insertSelective(orderShipping);
            return XinYuanResult.ok(orderIdString);
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage());
          return  XinYuanResult.build(405,"系统发生严重错误,原因是？ "+e.getMessage());
        }

    }
}
