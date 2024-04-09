package com.hixtrip.sample.domain.order.repository;

import com.hixtrip.sample.domain.order.model.Order;

/**
 *
 */
public interface OrderRepository {

    /**
     * 创建订单
     * @param order
     * @return
     */
    Integer createOrder(Order order);

    /**
     * 根据订单号获取订单信息
     * @param orderId
     * @return
     */
    Order getOrder(String orderId);

    /**
     * 更新订单状态
     * @param orderId
     * @param code
     */
    void updateOrderStatus(String orderId, String code);

}
