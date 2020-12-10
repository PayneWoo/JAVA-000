package com.payne.shardingspherejdbc.model;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 订单信息实体
 * @author Payne
 * @date 2020/12/10
 */
@Data
public class OrderInfo {

    private int id;
    private int orderId;
    private int userId;
    private String sellerId;
    private String goodsNo;
    private String snapshotId;
    private int businessType;
    private BigDecimal orderAmount;
    private BigDecimal payAmount;
    private int payWay;
    private int payChannel;
    private String payUrl;
    private String merchantPayNo;
    private String webPayNo;
    private int orderStatus;
    private long createTime;
    private long payTime;
    private long updateTime;

    public OrderInfo() {}

}
