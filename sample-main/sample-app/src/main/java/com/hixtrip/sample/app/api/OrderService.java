package com.hixtrip.sample.app.api;

import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.order.model.entity.PayOrderEntity;
import com.hixtrip.sample.domain.pay.model.CommandPay;

/**
 * 订单的service层
 */
public interface OrderService {

    PayOrderEntity createOrder(Order order);

}
