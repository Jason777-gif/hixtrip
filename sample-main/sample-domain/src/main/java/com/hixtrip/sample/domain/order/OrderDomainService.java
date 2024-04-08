package com.hixtrip.sample.domain.order;

import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.order.model.aggregate.CreateOrderAggregate;
import com.hixtrip.sample.domain.order.model.entity.OrderEntity;
import com.hixtrip.sample.domain.order.model.entity.PayOrderEntity;
import com.hixtrip.sample.domain.order.model.entity.ProductEntity;
import com.hixtrip.sample.domain.order.model.valobj.OrderStatusVO;
import com.hixtrip.sample.domain.order.repository.OrderRepository;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单领域服务
 * todo 只需要实现创建订单即可
 */
@Component
public class OrderDomainService {

    @Resource
    private OrderRepository orderRepository;

    /**
     * todo 需要实现
     * 创建待付款订单
     */
    public PayOrderEntity createOrder(Order order) {
        //需要你在infra实现, 自行定义出入参

        // 1. 查询当前用户是否存在掉单和未支付订单
        OrderEntity unpaidOrderEntity = orderRepository.queryUnPayOrder(order);
        if (null != unpaidOrderEntity && OrderStatusVO.PAY_WAIT.equals(unpaidOrderEntity.getOrderStatus())) {
           //创建订单-存在，已存在未支付订单
            return PayOrderEntity.builder()
                    .orderId(unpaidOrderEntity.getOrderId())
                    .payUrl(unpaidOrderEntity.getPayUrl())
                    .build();
        } else if (null != unpaidOrderEntity && OrderStatusVO.CREATE.equals(unpaidOrderEntity.getOrderStatus())) {
            //创建订单-存在，存在未创建支付单订单，创建支付单开始
            PayOrderEntity payOrderEntity = this.doPrepayOrder(order.getUserId(), order.getSkuId(), unpaidOrderEntity.getProductName(), unpaidOrderEntity.getOrderId(), unpaidOrderEntity.getTotalAmount());
            return PayOrderEntity.builder()
                    .orderId(payOrderEntity.getOrderId())
                    .payUrl(payOrderEntity.getPayUrl())
                    .build();
        }
        // 2. 查询商品 & 聚合订单
        ProductEntity productEntity = orderRepository.queryProductByProductId(order.getSkuId());
        OrderEntity orderEntity = OrderEntity.builder()
                .skuId(productEntity.getProductId())
                .productName(productEntity.getProductName())
                .orderId("66698522121324154")
                .orderTime(new Date())
                .orderStatus(OrderStatusVO.CREATE)
                .build();
        CreateOrderAggregate orderAggregate = CreateOrderAggregate.builder()
                .userId(order.getUserId())
                .productEntity(productEntity)
                .orderEntity(orderEntity)
                .build();

        // 3. 保存订单 - 保存一份订单，再用订单生成ID生成支付单信息
        this.doSaveOrder(orderAggregate);

        // 4. 创建支付单
        PayOrderEntity payOrderEntity = this.doPrepayOrder(order.getUserId(), productEntity.getProductId(), productEntity.getProductName(), orderEntity.getOrderId(), productEntity.getPrice());
        // 创建订单-完成，生成支付单
        return PayOrderEntity.builder()
                .orderId(payOrderEntity.getOrderId())
                .payUrl(payOrderEntity.getPayUrl())
                .build();
    }

    /**
     * 保存订单
     * @param orderAggregate
     */
    public void doSaveOrder(CreateOrderAggregate orderAggregate) {
        orderRepository.doSaveOrder(orderAggregate);
    }

    /**
     * 预支付订单生成
     * @param userId
     * @param skuId
     * @param productName
     * @param orderId
     * @param totalAmount
     * @return {@link PayOrderEntity}
     */
    public PayOrderEntity doPrepayOrder(String userId, String skuId, String productName, String orderId, BigDecimal totalAmount) {

        PayOrderEntity payOrderEntity = new PayOrderEntity();
        payOrderEntity.setOrderId(orderId);
        // todo 因没有引用第三方，现阶段随便写个值
        payOrderEntity.setPayUrl("http://www.baidu.com");
        payOrderEntity.setOrderStatus(OrderStatusVO.PAY_WAIT);

        // 更新订单支付信息
        orderRepository.updateOrderPayInfo(payOrderEntity);
        return payOrderEntity;
    }

    /**
     * todo 需要实现
     * 待付款订单支付成功
     */
    public void orderPaySuccess(CommandPay commandPay) {
        //需要你在infra实现, 自行定义出入参
        orderRepository.orderPaySuccess(commandPay);
    }

    /**
     * todo 需要实现
     * 待付款订单支付失败
     */
    public void orderPayFail(CommandPay commandPay) {
        //需要你在infra实现, 自行定义出入参
        orderRepository.orderPayFail(commandPay);
    }
}
