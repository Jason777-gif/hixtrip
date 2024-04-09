package com.hixtrip.sample.app.service.callback.impl;

import com.hixtrip.sample.app.service.callback.ICallBackHandler;
import com.hixtrip.sample.client.order.dto.CommandPayDTO;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author xgh 2024/4/8
 */
@Component
public class RepeatCallbackHandler implements ICallBackHandler {
    @Override
    public boolean canHandle(CommandPayDTO commandPay) {
        return Objects.equals("REPEAT", commandPay.getPayStatus());
    }

    @Override
    public void doHandle(CommandPayDTO commandPay) {
        // when repeat , do something
    }
}
