package com.hixtrip.sample.app.service.callback.impl;

import com.hixtrip.sample.app.service.callback.ICallBackHandler;
import com.hixtrip.sample.client.order.dto.CommandPayDTO;
import com.hixtrip.sample.domain.order.OrderDomainService;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author xgh 2024/4/8
 */
@Component
public class SuccessCallbackHandler implements ICallBackHandler {
    @Autowired
    OrderDomainService orderDomainService;
    @Override
    public boolean canHandle(CommandPayDTO commandPay) {
        return Objects.equals("SUCCESS", commandPay.getPayStatus());
    }

    @Override
    public void doHandle(CommandPayDTO commandPay) {
        CommandPay pay = new CommandPay();
        pay.setPayStatus(commandPay.getPayStatus());
        pay.setOrderId(commandPay.getOrderId());
        orderDomainService.orderPaySuccess(pay);
    }
}
