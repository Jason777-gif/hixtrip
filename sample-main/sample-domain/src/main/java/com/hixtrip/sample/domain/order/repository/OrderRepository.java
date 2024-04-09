package com.hixtrip.sample.domain.order.repository;

import com.hixtrip.sample.domain.order.model.Order;

/**
 *
 */
public interface OrderRepository {
    Order findById(String id);

    int createOrder(Order order);

    int updateStatus(Order order);
}
