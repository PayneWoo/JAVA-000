-- ----------------------------
-- Table structure for user_info
-- ----------------------------

CREATE TABLE `user_info` (

  `id` bigint(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(32) NOT NULL COMMENT '用户编号',
  `user_name` varchar(32) NOT NULL COMMENT '用户姓名',
  `user_sex` tinyint(5) unsigned DEFAULT 0 COMMENT '用户性别(0未知,1男,2女)',
  `user_birthdate` tinyint(10) unsigned DEFAULT NULL COMMENT '用户出生日期时间戳',
  `user_age` tinyint(5) unsigned DEFAULT NULL COMMENT '用户年龄',
  `head_url` varchar(128) DEFAULT NULL COMMENT '用户头像url',
  `buyer_id` varchar(32) NOT NULL COMMENT '用户作为买方的ID',
  `seller_id` varchar(32) NOT NULL COMMENT '用户作为卖方的ID',
  `user_phone` varchar(16) NOT NULL COMMENT '用户手机号',
  `user_address` varchar(256) NOT NULL COMMENT '用户收货地址',
  `alternate_address` varchar(256) NOT NULL COMMENT '用户备用收货地址',
  `pay_account` varchar(32) NOT NULL COMMENT '用户的支付账号',
  `receive_account` varchar(32) NOT NULL COMMENT '用户的收款账号',
  `pay_total` decimal(12, 4) DEFAULT NULL COMMENT '用户累计支付总额',
  `user_level` tinyint(5) unsigned DEFAULT 0 COMMENT '用户等级（0普通用户,1白银会员,2黄金会员,3钻石会员,其他）',
  `user_status` tinyint(5) unsigned DEFAULT 1 COMMENT '用户状态（0已锁定,1正常,2异常,3其他状态）',
  `login_way` tinyint(5) unsigned DEFAULT NULL COMMENT '用户登录方式（0微信,1支付宝,2微博,3其他）',
  `register_time` bigint(10) unsigned DEFAULT NULL COMMENT '用户注册时间戳',
  `update_time` bigint(10) unsigned DEFAULT NULL COMMENT '用户信息更新时间戳',

  PRIMARY KEY `pk_id` (`id`) COMMENT '主键索引',
  KEY `idx_user_phone` (`user_phone`) COMMENT '用户手机号-普通索引',
  KEY `idx_pay_account` (`pay_account`) COMMENT '用户支付账号-普通索引',
  KEY `idx_receive_account` (`receive_account`) COMMENT '用户首款账号-普通索引',
  UNIQUE KEY `uk_user_id` (`user_id`) COMMENT '用户编号-唯一索引',
  UNIQUE KEY `uk_buyer_id` (`buyer_id`) COMMENT '用户作为买方的ID-唯一索引',
  UNIQUE KEY `uk_seller_id` (`seller_id`) COMMENT '用户作为卖方的ID-唯一索引'
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for order_record
-- ----------------------------

CREATE TABLE `order_record` (

  `id` bigint(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `order_no` varchar(32) NOT NULL COMMENT '订单编号',
  `buyer_id` varchar(32) NOT NULL COMMENT '买方ID',
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

  PRIMARY KEY `pk_id` (`id`) COMMENT '主键索引',
  KEY `idx_order_no` (`order_no`) COMMENT '订单编号-普通索引',
  UNIQUE KEY `uk_snapshot_id` (`snapshot_id`) COMMENT '订单快照-唯一索引',
  UNIQUE KEY `uk_merchant_pay_no` (`merchant_pay_no`) COMMENT '商户支付流水号-唯一索引',
  UNIQUE KEY `uk_web_pay_no` (`web_pay_no`) COMMENT '第三方支付流水号-唯一索引'
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '订单表' ROW_FORMAT = Dynamic;



-- ----------------------------
-- Table structure for goods_info
-- ----------------------------

CREATE TABLE `goods_info`(

  `id` bigint(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `goods_no` varchar(32) NOT NULL COMMENT '商品编号',
  `goods_name` varchar(32) NOT NULL COMMENT '商品名称',
  `goods_desc` varchar(64) NOT NULL COMMENT '商品描述',
  `goods_barcode` varchar(64) NOT NULL COMMENT '商品条形码',
  `goods_img` varchar(128) DEFAULT NULL COMMENT '商品图片',
  `goods_type` tinyint(5) unsigned DEFAULT 0 COMMENT '商品类别(0未知,1办公类,2食品类,3其他类目)',
  `goods_reserve` tinyint(10) unsigned DEFAULT 0 COMMENT '商品库存',
  `goods_sale` tinyint(10) unsigned DEFAULT 0 COMMENT '商品销量',
  `goods_status` tinyint(5) unsigned DEFAULT 0 COMMENT '商品状态(0未上架,1已上架,2审核中,3违规下架,4其他)',
  `product_time` bigint(10) unsigned DEFAULT NULL COMMENT '生产日期时间戳',
  `expired_time` bigint(10) unsigned DEFAULT NULL COMMENT '过期日时间戳',
  `product_address` varchar(32) NOT NULL COMMENT '产地',
  `product_company` varchar(32) NOT NULL COMMENT '生产商',
  `company_address` varchar(32) NOT NULL COMMENT '生产商地址',
  `company_phone` varchar(32) NOT NULL COMMENT '生产商联系方式',
  `create_time` bigint(10) unsigned DEFAULT NULL COMMENT '商品创建时间戳',
  `update_time` bigint(10) unsigned DEFAULT NULL COMMENT '商品信息更新时间戳',

  PRIMARY KEY `pk_id` (`id`) COMMENT '主键索引',
  KEY `idx_company_phone` (`company_phone`) COMMENT '生产商联系方式-普通索引',
  UNIQUE KEY `uk_goods_no` (`goods_no`) COMMENT '商品编号-唯一索引',
  UNIQUE KEY `uk_goods_barcode` (`goods_barcode`) COMMENT '商品条形码-唯一索引'
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '商品表' ROW_FORMAT = Dynamic;
