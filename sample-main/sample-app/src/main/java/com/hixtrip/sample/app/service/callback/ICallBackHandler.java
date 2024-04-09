package com.hixtrip.sample.app.service.callback;

import com.hixtrip.sample.client.order.dto.CommandPayDTO;

/**
 * @author xgh 2024/4/8
 */
public interface ICallBackHandler {
    /**
     * 是否能过处理该状态
     *
     * @param status
     * @return
     */
    boolean canHandle(CommandPayDTO status);

    /**
     * 真正处理逻辑
     *
     * @param commandPay
     */
    void doHandle(CommandPayDTO commandPay);
}
