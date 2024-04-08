package com.hixtrip.sample.app.api;

import com.hixtrip.sample.domain.pay.model.CommandPay;

/**
 * 订单service
 * @author clw
 * @date 2024-03-07
 */
public interface PayService {
    void payRecord(CommandPay commandPay);
}
