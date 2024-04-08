CREATE TABLE `pay_order`  (
                              `id` bigint NOT NULL AUTO_INCREMENT COMMENT '订单id',
                              `member_id` bigint NOT NULL,
                              `coupon_id` bigint NULL DEFAULT NULL,
                              `order_sn` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单编号',
                              `create_time` datetime NULL DEFAULT NULL COMMENT '提交时间',
                              `member_username` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户帐号',
                              `total_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '订单总金额',
                              `pay_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '应付金额（实际支付金额）',
                              `freight_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '运费金额',
                              `promotion_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '促销优化金额（促销价、满减、阶梯价）',
                              `integration_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '积分抵扣金额',
                              `coupon_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '优惠券抵扣金额',
                              `discount_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '管理员后台调整订单使用的折扣金额',
                              `pay_type` int NULL DEFAULT NULL COMMENT '支付方式：0->未支付；1->支付宝；2->微信',
                              `source_type` int NULL DEFAULT NULL COMMENT '订单来源：0->PC订单；1->app订单',
                              `status` int NULL DEFAULT NULL COMMENT '订单状态：0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单',
                              `order_type` int NULL DEFAULT NULL COMMENT '订单类型：0->正常订单；1->秒杀订单',
                              `delivery_company` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '物流公司(配送方式)',
                              `delivery_sn` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '物流单号',
                              `auto_confirm_day` int NULL DEFAULT NULL COMMENT '自动确认时间（天）',
                              `integration` int NULL DEFAULT NULL COMMENT '可以获得的积分',
                              `growth` int NULL DEFAULT NULL COMMENT '可以活动的成长值',
                              `promotion_info` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '活动信息',
                              `bill_type` int NULL DEFAULT NULL COMMENT '发票类型：0->不开发票；1->电子发票；2->纸质发票',
                              `bill_header` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发票抬头',
                              `bill_content` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发票内容',
                              `bill_receiver_phone` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收票人电话',
                              `bill_receiver_email` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收票人邮箱',
                              `receiver_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '收货人姓名',
                              `receiver_phone` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '收货人电话',
                              `receiver_post_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收货人邮编',
                              `receiver_province` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '省份/直辖市',
                              `receiver_city` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '城市',
                              `receiver_region` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '区',
                              `receiver_detail_address` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '详细地址',
                              `note` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单备注',
                              `confirm_status` int NULL DEFAULT NULL COMMENT '确认收货状态：0->未确认；1->已确认',
                              `delete_status` int NOT NULL DEFAULT 0 COMMENT '删除状态：0->未删除；1->已删除',
                              `use_integration` int NULL DEFAULT NULL COMMENT '下单时使用的积分',
                              `payment_time` datetime NULL DEFAULT NULL COMMENT '支付时间',
                              `delivery_time` datetime NULL DEFAULT NULL COMMENT '发货时间',
                              `receive_time` datetime NULL DEFAULT NULL COMMENT '确认收货时间',
                              `comment_time` datetime NULL DEFAULT NULL COMMENT '评价时间',
                              `modify_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
                              PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 77 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '订单表' ROW_FORMAT = DYNAMIC;

CREATE TABLE `pay_order_item`  (
                                   `id` bigint NOT NULL AUTO_INCREMENT,
                                   `order_id` bigint NULL DEFAULT NULL COMMENT '订单id',
                                   `order_sn` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单编号',
                                   `product_id` bigint NULL DEFAULT NULL,
                                   `product_pic` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                   `product_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                   `product_brand` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                   `product_sn` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                   `product_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '销售价格',
                                   `product_quantity` int NULL DEFAULT NULL COMMENT '购买数量',
                                   `product_sku_id` bigint NULL DEFAULT NULL COMMENT '商品sku编号',
                                   `product_sku_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品sku条码',
                                   `product_category_id` bigint NULL DEFAULT NULL COMMENT '商品分类id',
                                   `promotion_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品促销名称',
                                   `promotion_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '商品促销分解金额',
                                   `coupon_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '优惠券优惠分解金额',
                                   `integration_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '积分优惠分解金额',
                                   `real_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '该商品经过优惠后的分解金额',
                                   `gift_integration` int NULL DEFAULT 0,
                                   `gift_growth` int NULL DEFAULT 0,
                                   `product_attr` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品销售属性',
                                   PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 115 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '订单中所包含的商品' ROW_FORMAT = DYNAMIC;

CREATE TABLE `order_operate_history`  (
                                              `id` bigint NOT NULL AUTO_INCREMENT,
                                              `order_id` bigint NULL DEFAULT NULL COMMENT '订单id',
                                              `operate_man` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作人：用户；系统；后台管理员',
                                              `create_time` datetime NULL DEFAULT NULL COMMENT '操作时间',
                                              `order_status` int NULL DEFAULT NULL COMMENT '订单状态：0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单',
                                              `note` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
                                              PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 44 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '订单操作历史记录' ROW_FORMAT = DYNAMIC;

CREATE TABLE `order_return_apply`  (
                                           `id` bigint NOT NULL AUTO_INCREMENT,
                                           `order_id` bigint NULL DEFAULT NULL COMMENT '订单id',
                                           `company_address_id` bigint NULL DEFAULT NULL COMMENT '收货地址表id',
                                           `product_id` bigint NULL DEFAULT NULL COMMENT '退货商品id',
                                           `order_sn` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单编号',
                                           `create_time` datetime NULL DEFAULT NULL COMMENT '申请时间',
                                           `member_username` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '会员用户名',
                                           `return_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '退款金额',
                                           `return_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '退货人姓名',
                                           `return_phone` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '退货人电话',
                                           `status` int NULL DEFAULT NULL COMMENT '申请状态：0->待处理；1->退货中；2->已完成；3->已拒绝',
                                           `handle_time` datetime NULL DEFAULT NULL COMMENT '处理时间',
                                           `product_pic` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品图片',
                                           `product_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品名称',
                                           `product_brand` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品品牌',
                                           `product_attr` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品销售属性：颜色：红色；尺码：xl;',
                                           `product_count` int NULL DEFAULT NULL COMMENT '退货数量',
                                           `product_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '商品单价',
                                           `product_real_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '商品实际支付单价',
                                           `reason` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '原因',
                                           `description` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
                                           `proof_pics` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '凭证图片，以逗号隔开',
                                           `handle_note` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '处理备注',
                                           `handle_man` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '处理人员',
                                           `receive_man` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收货人',
                                           `receive_time` datetime NULL DEFAULT NULL COMMENT '收货时间',
                                           `receive_note` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收货备注',
                                           PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 27 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '订单退货申请' ROW_FORMAT = DYNAMIC;

CREATE TABLE `order_return_reason`  (
                                            `id` bigint NOT NULL AUTO_INCREMENT,
                                            `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '退货类型',
                                            `sort` int NULL DEFAULT NULL,
                                            `status` int NULL DEFAULT NULL COMMENT '状态：0->不启用；1->启用',
                                            `create_time` datetime NULL DEFAULT NULL COMMENT '添加时间',
                                            PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '退货原因表' ROW_FORMAT = DYNAMIC;

 /*
     (1)订单表分库分表设计方案
     分库键：member_id，根据买家ID进行分库
     分表键：id，根据订单ID进行分表

     订单表总共分为10个库，每个库中包含10张表，共100张表
     表名格式：pay_order_0_0，其中0表示库的编号，0表示表的编号

     为了提高查询性能，对pay_order表的字段进行索引设计如下：
     1. 对member_id字段创建索引，用于买家查询
     2. 对id字段创建唯一索引，用于加速订单ID查询

     (2)订单商品表分库分表设计方案
     分库键：order_id，根据订单ID进行分库
     分表键：id，根据订单商品项ID进行分表

    订单商品表总共分为10个库，每个库中包含10张表，共100张表
    表名格式：pay_order_item_0_0，其中0表示库的编号，0表示表的编号
     为了提高查询性能，对pay_order_item表的字段进行索引设计如下：
     1. 对order_id字段创建索引，用于买家查询
     2. 对id字段创建唯一索引，用于加速订单商品项ID查询

     为了满足卖家查询需求，可以在订单表中增加一个卖家ID字段seller_id，然后针对该字段创建索引：
     3. 对seller_id字段创建索引，用于卖家查询
    这样的分库分表设计结合索引的方式可以更好地满足买卖双方的查询需求，提高系统性能和响应速度。
*/