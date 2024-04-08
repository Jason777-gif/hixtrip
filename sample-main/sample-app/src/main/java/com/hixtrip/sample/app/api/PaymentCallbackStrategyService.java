package com.hixtrip.sample.app.api;

import com.hixtrip.sample.domain.pay.model.CommandPay;

/**
 *  定义支付成功处理策略
 * @author clw
 * @date 2024-03-07
 */
public interface PaymentCallbackStrategyService {
    void handleCallback(CommandPay commandPay);
}
