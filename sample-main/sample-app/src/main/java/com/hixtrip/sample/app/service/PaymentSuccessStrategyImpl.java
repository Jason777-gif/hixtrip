package com.hixtrip.sample.app.service;

import com.hixtrip.sample.app.api.PaymentCallbackStrategyService;
import com.hixtrip.sample.domain.order.OrderDomainService;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 支付成功处理策略实现类
 * @author clw
 * @date 2024-03-07
 */
@Component
public class PaymentSuccessStrategyImpl implements PaymentCallbackStrategyService {
    @Autowired
    private OrderDomainService orderDomainService;
    @Override
    public void handleCallback(CommandPay commandPay) {
        //更新订单
        orderDomainService.orderPaySuccess(commandPay);
        // todo 使用 Guava 的 EventBus 消息总线来模拟消息使用。如果你后续的场景较大，也可以替换为 MQ
        // todo 因为没有引入相关包所以代码注释掉
        // eventBus.post(commandPay.getOrderId());
    }
}
