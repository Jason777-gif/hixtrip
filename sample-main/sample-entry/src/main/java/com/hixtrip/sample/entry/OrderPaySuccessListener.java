package com.hixtrip.sample.entry;

import com.hixtrip.sample.app.api.OrderService;
import com.hixtrip.sample.app.api.PayService;
import com.hixtrip.sample.domain.order.model.valobj.OrderStatusVO;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


/**
 * 订单支付成功监听
 * 订单支付成功回调，最好是快速变更订单状态，避免超时重试次数上限后不能做业务。所以推送出MQ消息来做【发货】流程
 * 用 Guava 的 EventBus 消息总线来模拟消息使用。如果你后续的场景较大，也可以替换为RabbitMQ
 * @author clw
 * @date 2024-03-07
 */
@Component
@Slf4j
public class OrderPaySuccessListener {
    @Resource
    private OrderService orderService;
    @Resource
    private PayService payService;
    // @Subscribe此注解是guava提供的

    // @Subscribe
    public void handleEvent(String orderId) {
        try {
            // 具体没有实现这里提供思路
            CommandPay commandPay = new CommandPay();
            commandPay.setOrderId(orderId);
            commandPay.setPayStatus(OrderStatusVO.PAY_SUCCESS.getCode());
            payService.payRecord(commandPay);
            log.info("支付完成，发货并记录，开始。订单：{}", orderId);
        } catch (Exception e) {
            log.error("支付完成，发货并记录，失败。订单：{}", orderId, e);
        }
    }
}
