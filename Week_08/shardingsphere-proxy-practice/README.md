# 作业描述

设计对前面的订单表数据进行水平分库分表，拆分 2 个库，每个库 16 张表。
并在新结构在演示常见的增删改查操作。


## 配置并运行 ShardingSphere-Proxy

### 1.下载好 apache-shardingsphere-5.0.0-alpha-shardingsphere-proxy-bin 并解压
### 2.下载一个 MySQL 的 JDBC 驱动,将其放入 shardingsphere-proxy 的 bin 目录下
### 3.配置 shardingsphere-proxy，主要是 server.yaml 和 config-sharding.yaml

#### 配置 server.yaml
```

######################################################################################################
#
# If you want to configure governance, authorization and proxy properties, please refer to this file.
#
######################################################################################################
#
#governance:
#  name: governance_ds
#  registryCenter:
#    type: ZooKeeper
#    serverLists: localhost:2181
#    props:
#      retryIntervalMilliseconds: 500
#      timeToLiveSeconds: 60
#      maxRetries: 3
#      operationTimeoutMilliseconds: 500
#  overwrite: false

authentication:
 users:
   root:
     password: root
   sharding:
     password: sharding
     authorizedSchemas: sharding_db

props:
 max-connections-size-per-query: 1
 acceptor-size: 16  # The default value is available processors count * 2.
 executor-size: 16  # Infinite by default.
 proxy-frontend-flush-threshold: 128  # The default value is 128.
   # LOCAL: Proxy will run with LOCAL transaction.
   # XA: Proxy will run with XA transaction.
   # BASE: Proxy will run with B.A.S.E transaction.
 proxy-transaction-type: LOCAL
 proxy-opentracing-enabled: false
 proxy-hint-enabled: false
 query-with-cipher-column: true
 sql-show: true
 check-table-metadata-enabled: false

```

#### 配置 config-sharding.yaml
```
# ShardingSphere 数据库名
schemaName: test_proxy

dataSourceCommon:
 username: root
 password: 123456
 connectionTimeoutMilliseconds: 30000
 idleTimeoutMilliseconds: 60000
 maxLifetimeMilliseconds: 1800000
 maxPoolSize: 50
 minPoolSize: 1
 maintenanceIntervalMilliseconds: 30000

# 数据源
dataSources:
 test0:
   url: jdbc:mysql://127.0.0.1:3306/test0?serverTimezone=UTC&useSSL=false
 test1:
   url: jdbc:mysql://127.0.0.1:3306/test1?serverTimezone=UTC&useSSL=false

rules:
- !SHARDING
 tables:
   t_order:
     actualDataNodes: test${0..1}.t_order_${0..15}
     tableStrategy:
       standard:
         shardingColumn: order_id
         shardingAlgorithmName: t_order_inline
 #     keyGenerateStrategy:
 #       column: order_id
 #       keyGeneratorName: snowflake
 #   t_order_item:
 #     actualDataNodes: test${0..1}.t_order_item_${0..2}
 #     tableStrategy:
 #       standard:
 #         shardingColumn: order_id
 #         shardingAlgorithmName: t_order_item_inline
 #     keyGenerateStrategy:
 #       column: order_item_id
 #       keyGeneratorName: snowflake
 # bindingTables:
 #   - t_order,t_order_item
 defaultDatabaseStrategy:
   standard:
     shardingColumn: user_id
     shardingAlgorithmName: database_inline
 # defaultTableStrategy:
 #   none:

 shardingAlgorithms:
   database_inline:
     type: INLINE
     props:
       algorithm-expression: test${user_id % 2}
   t_order_inline:
     type: INLINE
     props:
       algorithm-expression: t_order_${order_id % 16}
   # t_order_item_inline:
   #   type: INLINE
   #   props:
   #     algorithm-expression: t_order_item_${order_id % 3}

 # keyGenerators:
 #   snowflake:
 #     type: SNOWFLAKE
 #     props:
 #       worker-id: 123

```

### 启动 shardingsphere-proxy
```
# 默认 3307 端口启动；也可以启动时指定端口，如 ./start.ch 3308
./start.sh 
```
启动成功会有日志输出,如有报错，可根据具体报错日志，调整配置文件
```
[main] o.a.s.p.frontend.ShardingSphereProxy - ShardingSphere-Proxy start success.
```

### 通过命令行连接 ShardingSphereProxy 代理出来的 MySQL 库 test_proxy
```
mysql -uroot -proot -h127.0.0.1 -P3307 -Dtest_proxy
```

### 创建 t_order 表

通过命令行创建 t_order 表，此时 ShardingSphereProxy 会在 test0 和 test1库各建16个表: t_order{0..15}
```
CREATE TABLE IF NOT EXISTS `t_order` (
    `id` bigint(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
    `order_id` bigint(11) NOT NULL COMMENT '订单编号',
    `user_id` bigint(32) NOT NULL COMMENT '买方ID',
    `seller_id` varchar(32) NOT NULL COMMENT '卖方ID',
    `goods_no` varchar(32) NOT NULL COMMENT '订单商品的编号',
    `snapshot_id` varchar(32) NOT NULL COMMENT '交易时商品的快照ID',
    `business_type` tinyint(5) unsigned DEFAULT NULL COMMENT '订单的业务类型',
    `order_amount` decimal(12, 4) NOT NULL COMMENT '订单金额',
    `pay_amount` decimal(12, 4) NOT NULL COMMENT '实际支付金额',
    `pay_way` tinyint(5) unsigned NOT NULL COMMENT '支付方式',
    `pay_channel` tinyint(5) unsigned DEFAULT NULL COMMENT '支付渠道',
    `pay_url` varchar(256) DEFAULT NULL COMMENT '支付地址',
    `merchant_pay_no` varchar(32) DEFAULT NULL COMMENT '商户支付流水号',
    `web_pay_no` varchar(64) DEFAULT NULL COMMENT '第三方支付流水号',
    `order_status` tinyint(5) unsigned DEFAULT 0 COMMENT '订单状态 (0未支付,1已支付,2支付未确认,3已退费,4部分退费,5确认失败)',
    `create_time` bigint(10) unsigned DEFAULT NULL COMMENT '订单创建时间戳',
    `pay_time` bigint(10) unsigned DEFAULT NULL COMMENT '订单支付时间戳',
    `update_time` bigint(10) unsigned DEFAULT  NULL COMMENT '订单更新时间戳',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;
```


## 测试 ShardingSphereProxy 

### 测试类 ReadWriteTest.java

#### 1.直接走 test1 库查数据
```
    /**
     * 3 % 2 = 1, 所以会直接从 test1 库查数据
     */
    @Test
    public void queryByUserId() {

        HashMap<String, Object> map = new HashMap<>();
        map.put("userId", 3);
        OrderInfo orderInfo = orderInfoService.getOrderInfoByOrderId(map);
        log.info("查数据：{}", orderInfo);
    }
```
查 test1 库 的 16 个表：

![直接走 test1 库查数据](https://i.loli.net/2020/12/11/EjdXKJGnwkvWpPs.png)

#### 2.直接走 t_order_15 表查数据
```
    /**
     * 111 % 16 = 15, 所以会从 test0和test1库的 t_order_15 表查数据
     */
    @Test
    public void queryByOrderId() {

        HashMap<String, Object> map = new HashMap<>();
        map.put("orderId", 111);
        OrderInfo orderInfo = orderInfoService.getOrderInfoByOrderId(map);
        log.info("查数据：{}", orderInfo);
    }

```
![T9SB3XVepu5N2dq](https://i.loli.net/2020/12/11/T9SB3XVepu5N2dq.png)

#### 3.直接走 test1 库 的 t_order_15 表查数据
```
    /**
     * 直接从 test1 库的 t_order_15 表查数据
     */
    @Test
    public void queryByUserIdAndOrderId() {

        HashMap<String, Object> map = new HashMap<>();
        map.put("userId", 3);
        map.put("orderId", 111);
        OrderInfo orderInfo = orderInfoService.getOrderInfoByOrderId(map);
        log.info("查数据：{}", orderInfo);
    }
```
![r2tAQezgFHkvq3h](https://i.loli.net/2020/12/11/r2tAQezgFHkvq3h.png)

#### 4.测试插入数据：会根据 userId 和 orderId 插入到指定的库表
```
    /**
     *
     * 直接写入数据到 test1 库的 t_order_15 表
     */
    @Test
    public void testWrite() {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setId(1);
        orderInfo.setUserId(3);
        orderInfo.setOrderId(111);
        orderInfo.setSellerId("seller0001");
        orderInfo.setGoodsNo("I000001");
        orderInfo.setSnapshotId("K0000001");
        orderInfo.setBusinessType(1);
        orderInfo.setOrderAmount(new BigDecimal("90.1"));
        orderInfo.setPayAmount(new BigDecimal("90.1"));
        orderInfo.setPayWay(1);
        orderInfo.setPayChannel(1);
        orderInfo.setPayUrl("https://pay.payne.com");
        orderInfo.setMerchantPayNo("82384875");
        orderInfo.setWebPayNo("1289238498");
        orderInfo.setOrderStatus(1);
        String result = orderInfoService.save(orderInfo);
        log.info("写数据结果：{}", result);
    }
```
![Mugaw9pPeZFIC7U](https://i.loli.net/2020/12/11/Mugaw9pPeZFIC7U.png)


#### 测试删除语句：
![6C35LnpPcqSBv9Q](https://i.loli.net/2020/12/11/6C35LnpPcqSBv9Q.png)


#### 测试更新语句：
![v9IGl6juEyFkRAS](https://i.loli.net/2020/12/11/v9IGl6juEyFkRAS.png)

