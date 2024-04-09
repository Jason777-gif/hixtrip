package com.hixtrip.sample.domain.pay.handler;

import com.hixtrip.sample.domain.order.OrderDomainService;
import com.hixtrip.sample.domain.pay.enums.PayStatusEnum;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 支付失败
 */
@Component
public class PaidFailHandler implements PayCallbackHandler{

    @Autowired
    private OrderDomainService orderDomainService;

    @Override
    public PayStatusEnum getHandlerType() {
        return PayStatusEnum.PAID_FAIL;
    }

    @Override
    public void execution(CommandPay commandPay) {
        orderDomainService.orderPayFail(commandPay);
    }

}
