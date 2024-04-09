package com.hixtrip.sample.domain.pay.handler;

import com.hixtrip.sample.domain.pay.enums.PayStatusEnum;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class PayCallbackHandlerFactory implements ApplicationContextAware {

    private Map<PayStatusEnum, PayCallbackHandler> HANDLER_MAP = new HashMap<>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, PayCallbackHandler> payCallbackTypeHandlerMap = applicationContext.getBeansOfType(PayCallbackHandler.class);
        payCallbackTypeHandlerMap.values().forEach(payCallbackTypeHandler -> {
            HANDLER_MAP.put(payCallbackTypeHandler.getHandlerType(), payCallbackTypeHandler);
        });
    }

    public PayCallbackHandler getHandler(String PayStatus) {
        PayStatusEnum payStatusEnum = PayStatusEnum.getByCode(PayStatus);
        return HANDLER_MAP.get(payStatusEnum);
    }

}
