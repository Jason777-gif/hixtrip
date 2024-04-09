package com.hixtrip.sample.app.service;

import com.hixtrip.sample.app.api.OrderService;
import com.hixtrip.sample.app.convertor.CommandPayConvertor;
import com.hixtrip.sample.app.convertor.OrderConvertor;
import com.hixtrip.sample.client.order.dto.CommandOderCreateDTO;
import com.hixtrip.sample.client.order.dto.CommandPayDTO;
import com.hixtrip.sample.domain.order.OrderDomainService;
import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.pay.PayDomainService;
import com.hixtrip.sample.domain.pay.enums.PayStatusEnum;
import com.hixtrip.sample.domain.pay.handler.PayCallbackHandler;
import com.hixtrip.sample.domain.pay.handler.PayCallbackHandlerFactory;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * app层负责处理request请求，调用领域服务
 */
@Component
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDomainService orderDomainService;

    @Autowired
    private PayDomainService payDomainService;

    @Autowired
    private PayCallbackHandlerFactory payCallbackTypeHandlerFactory;

    @Override
    public String order(CommandOderCreateDTO commandOderCreateDTO) {
        Order order = OrderConvertor.INSTANCE
                .convertCommandOderCreateDTOToOrder(commandOderCreateDTO);
        return orderDomainService.createOrder(order);
    }

    @Override
    public void payCallback(CommandPayDTO commandPayDTO) {
        CommandPay commandPay = CommandPayConvertor
                .INSTANCE.convertCommandPayDTOToCommandPay(commandPayDTO);
        payDomainService.payRecord(commandPay);
        // 自定义回调报文只考虑支付成功（paid_success）和支付失败（paid_fail）
        String payStatus = commandPay.getPayStatus();
        // 支付成功，查订单对应支付状态码，已支付判断为重复支付
        if (payStatus.equals(PayStatusEnum.PAID_SUCCESS)) {
            String orderId = commandPayDTO.getOrderId();
            Order order = orderDomainService.getOrder(orderId);
            if (PayStatusEnum.PAID_SUCCESS.equals(order.getPayStatus())) {
                // 订单状态是已支付状态，判断为重复支付
                payStatus = PayStatusEnum.PAID_REPEATED.getCode();
            }
        }
        PayCallbackHandler handler = payCallbackTypeHandlerFactory.getHandler(payStatus);
        handler.execution(commandPay);
    }

}
