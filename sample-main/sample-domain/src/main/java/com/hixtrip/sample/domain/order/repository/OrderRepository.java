package com.hixtrip.sample.domain.order.repository;

import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.order.model.aggregate.CreateOrderAggregate;
import com.hixtrip.sample.domain.order.model.entity.OrderEntity;
import com.hixtrip.sample.domain.order.model.entity.PayOrderEntity;
import com.hixtrip.sample.domain.order.model.entity.ProductEntity;
import com.hixtrip.sample.domain.pay.model.CommandPay;

/**
 *
 */
public interface OrderRepository {

    /**
     * 查询未支付订单
     * @param order
     */
    OrderEntity queryUnPayOrder(Order order);

    /**
     * 更新订单支付信息
     * @param payOrderEntity
     */
    void updateOrderPayInfo(PayOrderEntity payOrderEntity);

    /**
     * 模拟查询商品信息
     * @param skuId
     * @return {@link ProductEntity}
     */
    ProductEntity queryProductByProductId(String skuId);

    /**
     * 保存订单对象
     * @param orderAggregate
     */
    void doSaveOrder(CreateOrderAggregate orderAggregate);

    /**
     * 订单支付成功
     * @param commandPay
     */
    boolean orderPaySuccess(CommandPay commandPay);

    boolean orderPayFail(CommandPay commandPay);
}
