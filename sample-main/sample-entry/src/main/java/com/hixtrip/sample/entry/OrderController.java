package com.hixtrip.sample.entry;

import com.hixtrip.sample.app.api.OrderService;
import com.hixtrip.sample.app.api.PaymentCallbackStrategyService;
import com.hixtrip.sample.app.factory.PaymentCallbackFactory;
import com.hixtrip.sample.client.order.dto.CommandOderCreateDTO;
import com.hixtrip.sample.client.order.dto.CommandPayDTO;
import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.order.model.entity.PayOrderEntity;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * todo 这是你要实现的
 */
@RestController
public class OrderController{

    @Autowired
    private OrderService orderService;

    @Autowired
    private PaymentCallbackFactory paymentCallbackFactory;

    /**
     * todo 这是你要实现的接口
     *
     * @param commandOderCreateDTO 入参对象
     * @return 请修改出参对象
     */
    @PostMapping(path = "/command/order/create")
    public PayOrderEntity order(@RequestBody CommandOderCreateDTO commandOderCreateDTO) {
        //登录信息可以在这里模拟
        // todo 正常情况下，这里需要根据token信息获取到用户的具体信息
        String userId = commandOderCreateDTO.getUserId();
        // 构造所需参数，这里只是列举了几个参数，正式需要根据实际情况来
        Order order = new Order();
        order.setUserId(userId);
        order.setSkuId(order.getSkuId());
        order.setAmount(1);
        order.setMoney(BigDecimal.valueOf(66));
        order.setPayTime(LocalDateTime.now());
        PayOrderEntity payOrder = orderService.createOrder(order);
        return payOrder;
    }

    /**
     * todo 这是模拟创建订单后，支付结果的回调通知
     * 【中、高级要求】需要使用策略模式处理至少三种场景：支付成功、支付失败、重复支付(自行设计回调报文进行重复判定)
     *
     * @param commandPayDTO 入参对象
     * @return 请修改出参对象
     */
    @PostMapping(path = "/command/order/pay/callback")
    public void payCallback(@RequestBody CommandPayDTO commandPayDTO) {
        // todo 只是例子这里入参没有看到一些回调所用到的参数
        // 这里需要根据回调报文来判断支付结果，并返回支付结果给前端
        String status = "success"; // 假设这里是微信支付回调的状态
        PaymentCallbackFactory callbackFactory = new PaymentCallbackFactory();
        PaymentCallbackStrategyService strategy = PaymentCallbackFactory.createCallbackStrategy(status);
        CommandPay commandPay = new CommandPay();
        commandPay.setOrderId(commandPayDTO.getOrderId());
        commandPay.setPayStatus(commandPayDTO.getPayStatus());
        strategy.handleCallback(commandPay);

    }

}
