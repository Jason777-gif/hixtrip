package com.hixtrip.sample.app.api;

import com.hixtrip.sample.client.order.dto.CommandOderCreateDTO;
import com.hixtrip.sample.client.order.dto.CommandPayDTO;

/**
 * 订单的service层
 */
public interface OrderService {


    /**
     * @Description: 生成订单
     * @param commandOderCreateDTO
     * @return: String
     * @version v1.0
     * @author chenzx
     * @date 2024/4/19
     */
    String createOrder(CommandOderCreateDTO commandOderCreateDTO);

    /**
     * @Description: 支付回调 需要使用策略模式处理至少三种场景：支付成功、支付失败、重复支付(自行设计回调报文进行重复判定)
     * @param commandPayDTO
     * @return: String
     * @version v1.0
     * @author chenzx
     * @date 2024/4/19
     */
    String payCallback(CommandPayDTO commandPayDTO);
}
