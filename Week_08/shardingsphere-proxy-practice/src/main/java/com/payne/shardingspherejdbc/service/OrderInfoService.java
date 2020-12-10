package com.payne.shardingspherejdbc.service;



import com.payne.shardingspherejdbc.model.OrderInfo;

import java.util.HashMap;
import java.util.List;


/**
 * @author payne
 */
public interface OrderInfoService {

    /**
     * 获取所有订单信息
     * @return 订单信息
     */
    List<OrderInfo> list();


    /**
     * 保存订单信息
     * @param orderInfo 订单信息
     * @return 保存结果
     */
    String save(OrderInfo orderInfo);


    OrderInfo getOrderInfoByOrderId(HashMap<String, Object> map);

}