package com.payne.shardingspherejdbc.service.impl;


import com.payne.shardingspherejdbc.mapper.OrderInfoMapper;
import com.payne.shardingspherejdbc.model.OrderInfo;
import com.payne.shardingspherejdbc.service.OrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;


/**
 * @author payne
 */
@Service
public class OrderInfoServiceImpl implements OrderInfoService {

    private final OrderInfoMapper orderInfoMapper;
    @Autowired
    public OrderInfoServiceImpl(OrderInfoMapper orderInfoMapper) {
        this.orderInfoMapper = orderInfoMapper;
    }

    @Override
    public List<OrderInfo> list() {
        return orderInfoMapper.selectAll();
    }

    @Override
    public String save(OrderInfo orderInfo) {

        orderInfoMapper.insert(orderInfo);
        return "保存成功";
    }

    @Override
    public OrderInfo getOrderInfoByOrderId(HashMap<String, Object> map) {

        return orderInfoMapper.selectByOrderId(map);
    }
}
