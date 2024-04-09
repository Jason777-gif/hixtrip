package com.hixtrip.sample.domain.pay.handler;

import com.hixtrip.sample.domain.pay.enums.PayStatusEnum;
import com.hixtrip.sample.domain.pay.model.CommandPay;

public interface PayCallbackHandler {

    /**
     * 枚举身份的识别
     * @return
     */
    PayStatusEnum getHandlerType();

    /**
     * 执行操作
     * @param commandPay
     */
    void execution(CommandPay commandPay);

}
