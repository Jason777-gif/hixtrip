package com.hixtrip.sample.app.service.callback.impl;

import com.hixtrip.sample.app.service.callback.ICallBackHandler;
import com.hixtrip.sample.client.order.dto.CommandPayDTO;
import com.hixtrip.sample.domain.pay.PayDomainService;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author xgh 2024/4/9
 */
public class RecordCallbackHandler implements ICallBackHandler {
    @Autowired
    PayDomainService payDomainService;

    @Override
    public boolean canHandle(CommandPayDTO status) {
        return true;
    }

    @Override
    public void doHandle(CommandPayDTO commandPay) {
        CommandPay pay = new CommandPay();
        pay.setPayStatus(commandPay.getPayStatus());
        pay.setOrderId(commandPay.getOrderId());
        payDomainService.payRecord(pay);
    }
}
