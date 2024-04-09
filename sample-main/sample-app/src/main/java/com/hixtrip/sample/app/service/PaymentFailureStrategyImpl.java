package com.hixtrip.sample.app.service;

import com.hixtrip.sample.app.api.PaymentCallbackStrategyService;
import com.hixtrip.sample.domain.pay.model.CommandPay;

/**
 * 支付失败处理策略实现类
 * @author clw
 * @date 2024-03-07
 */
public class PaymentFailureStrategyImpl implements PaymentCallbackStrategyService {
    @Override
    public void handleCallback(CommandPay commandPay) {

    }
}
