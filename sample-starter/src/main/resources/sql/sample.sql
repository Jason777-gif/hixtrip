#todo 你的建表语句,包含索引
CREATE TABLE IF NOT EXISTS `order` (
    `id` varchar(36) NOT NULL COMMENT '订单号',
    `user_id` varchar(36) NOT NULL COMMENT '购买人',
    `sku_id` varchar(36) NOT NULL COMMENT 'SkuId',
    `amount` int(11) NOT NULL COMMENT '购买数量',
    `money` decimal(10, 2) NOT NULL COMMENT '购买金额',
    `pay_time` datetime NULL COMMENT '购买时间',
    `pay_status` varchar(20) NOT NULL COMMENT '支付状态',
    `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标志（0代表存在，1代表删除）',
    `create_by` varchar(36) DEFAULT NULL COMMENT '创建人',
    `create_time` datetime NOT NULL COMMENT '创建时间',
    `update_by` varchar(36) DEFAULT NULL COMMENT '修改人',
    `update_time` datetime  NULL COMMENT '修改时间',

    PRIMARY KEY (`id`),
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';

create index i_user_order on order
    (
     user_id
        )  COMMENT '买家查询我的订单索引';
create index i_sku_order on order
    (
     sku_id
        )  COMMENT '卖家通过商品id查询我的订单索引';

