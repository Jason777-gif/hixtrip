package com.hixtrip.sample.domain.order;

import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.order.repository.OrderRepository;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.springframework.stereotype.Component;

/**
 * 订单领域服务
 */
@Component
public class OrderDomainService {
    OrderRepository orderRepository;

    /**
     * 创建待付款订单
     */
    public void createOrder(Order order) {
        orderRepository.createOrder(order);
    }

    /**
     * 待付款订单支付成功
     */
    public void orderPaySuccess(CommandPay commandPay) {
        Order order = orderRepository.findById(commandPay.getOrderId());
        order.changStatusToSuccess();
        orderRepository.updateStatus(order);
    }

    /**
     * 待付款订单支付失败
     */
    public void orderPayFail(CommandPay commandPay) {
        Order order = orderRepository.findById(commandPay.getOrderId());
        order.changStatusToFail();
        orderRepository.updateStatus(order);
    }
}
