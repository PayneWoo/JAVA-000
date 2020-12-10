package com.payne.shardingspherejdbc.mapper;

import com.payne.shardingspherejdbc.model.OrderInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

/**
 * @author payne
 */
@Mapper
public interface OrderInfoMapper {

    /**
     * 插入一条记录
     * @param orderInfo 实体对象
     * @return 更新条目数
     */
    int insert(OrderInfo orderInfo);

    /**
     * 获取所有订单
     */
    List<OrderInfo> selectAll();

    OrderInfo selectByOrderId(HashMap<String, Object> map);

}