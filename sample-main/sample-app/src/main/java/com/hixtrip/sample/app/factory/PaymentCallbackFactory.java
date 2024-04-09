package com.hixtrip.sample.app.factory;

import com.hixtrip.sample.app.api.PaymentCallbackStrategyService;
import com.hixtrip.sample.app.service.DuplicatePaymentStrategyImpl;
import com.hixtrip.sample.app.service.PaymentFailureStrategyImpl;
import com.hixtrip.sample.app.service.PaymentSuccessStrategyImpl;
import org.springframework.stereotype.Service;

/**
 * // 支付回调处理工厂类
 * 这里用的是简单工厂模式，更优的做法是用工厂方法模式
 * @author clw
 * @date 2024-03-07
 */
@Service
public class PaymentCallbackFactory {
    public static PaymentCallbackStrategyService createCallbackStrategy(String status) {
        switch (status) {
            case "success":
                return new PaymentSuccessStrategyImpl();
            case "fail":
                return new PaymentFailureStrategyImpl();
            case "duplicate":
                return new DuplicatePaymentStrategyImpl();
            default:
                throw new IllegalArgumentException("Unsupported status: " + status);
        }
    }
}
