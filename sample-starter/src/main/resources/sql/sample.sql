#todo 你的建表语句,包含索引
CREATE TABLE `order`  (
 `id` varchar(32) NOT NULL COMMENT '订单号',
 `user_id` varchar(32) NOT NULL COMMENT '买家',
 `seller_id` varchar(32) NOT NULL COMMENT '卖家',
 `sku_id` varchar(32) NOT NULL COMMENT 'SkuId',
 `amount` int(11) NOT NULL COMMENT '购买数量',
 `money` decimal(10,2) NOT NULL COMMENT '购买金额',
 `pay_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '购买时间',
 `pay_status` varchar(11) NOT NULL COMMENT '支付状态',
 `del_flag` tinyint(4) NOT NULL DEFAULT 0 COMMENT '删除标志（0代表存在 1代表删除）',
 `create_by` varchar(32) NOT NULL COMMENT '创建人',
 `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
 `update_by` varchar(32) NOT NULL COMMENT '修改人',
 `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
 PRIMARY KEY (`id`)
) COMMENT = '订单表';

# 买家优化查询
# `user_id`建立索引
# 按照买家id做分表 保证同一个买家的所有订单都在同一张表
# 买家id或者买家id的hashcode对1024取模存储到对应的编号的分表中就行了

# 卖家优化查询
# `seller_id`建立索引
# 订单数据插入同时实时同步到一张以卖家维度的表 这张表只有读操作
