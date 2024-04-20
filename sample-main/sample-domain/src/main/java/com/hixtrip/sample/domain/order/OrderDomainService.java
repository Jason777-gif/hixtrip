package com.hixtrip.sample.domain.order;

import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.order.repository.OrderRepository;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import com.hixtrip.sample.domain.sample.repository.SampleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 订单领域服务
 * todo 只需要实现创建订单即可
 */
@Component
public class OrderDomainService {

    @Autowired
    private OrderRepository orderRepository;
    /**
     * todo 需要实现
     * 创建待付款订单
     */
    public void createOrder(Order order) {
        orderRepository.createOrder(order);
        //需要你在infra实现, 自行定义出入参
    }


    /**
     * @Description: 通过id获取订单信息
     * @param id
     * @return: null
     * @version v1.0
     * @author chenzx
     * @date 2024/4/19
     */
    public Order selectOrderById(String id) {
       return orderRepository.selectOrderById(id);
    }

    /**
     * todo 需要实现
     * 待付款订单支付成功
     */
    public String orderPaySuccess(CommandPay commandPay) {
        //需要你在infra实现, 自行定义出入参
       return orderRepository.orderPaySuccess(commandPay);
    }

    /**
     * todo 需要实现
     * 待付款订单支付失败
     */
    public String orderPayFail(CommandPay commandPay) {
        //需要你在infra实现, 自行定义出入参
        return orderRepository.orderPayFail(commandPay);
    }
}
