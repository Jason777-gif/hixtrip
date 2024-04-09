package com.hixtrip.sample.infra;

import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.order.model.aggregate.CreateOrderAggregate;
import com.hixtrip.sample.domain.order.model.entity.OrderEntity;
import com.hixtrip.sample.domain.order.model.entity.PayOrderEntity;
import com.hixtrip.sample.domain.order.model.entity.ProductEntity;
import com.hixtrip.sample.domain.order.model.valobj.OrderStatusVO;
import com.hixtrip.sample.domain.order.repository.OrderRepository;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import com.hixtrip.sample.infra.db.mapper.OrderMapper;
import com.hixtrip.sample.infra.po.PayOrder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Component
public class OrderRepositoryImpl implements OrderRepository {

    @Resource
    private OrderMapper orderMapper;

    @Override
    public OrderEntity queryUnPayOrder(Order order) {

        // 1. 封装参数
        PayOrder orderReq = new PayOrder();
        orderReq.setUserId(order.getUserId());
        orderReq.setSkuId(order.getSkuId());
        // 2. 查询到订单
        PayOrder payOrder = orderMapper.queryUnPayOrder(orderReq);
        if (null == order){
            return null;
        }
        // 3. 返回结果
        return OrderEntity.builder()
                .skuId(payOrder.getSkuId())
                .productName(payOrder.getProductName())
                .orderId(payOrder.getOrderId())
                .orderStatus(OrderStatusVO.valueOf(payOrder.getStatus()))
                .orderTime(payOrder.getOrderTime())
                .totalAmount(payOrder.getTotalAmount())
                .payUrl(payOrder.getPayUrl())
                .build();
    }

    /**
     * 更新订单支付信息
     *
     * @param payOrderEntity
     */
    @Override
    public void updateOrderPayInfo(PayOrderEntity payOrderEntity) {
        PayOrder order = new PayOrder();
        order.setUserId(payOrderEntity.getUserId());
        order.setOrderId(payOrderEntity.getOrderId());
        order.setPayUrl(payOrderEntity.getPayUrl());
        order.setStatus(payOrderEntity.getOrderStatus().getCode());
        orderMapper.updateOrderPayInfo(order);
    }

    /**
     * 模拟查询商品信息
     *
     * @param skuId
     * @return {@link ProductEntity}
     */
    @Override
    public ProductEntity queryProductByProductId(String skuId) {
        // 实际场景中会从数据库查询
        return ProductEntity.builder()
                .productId(skuId)
                .productName("测试商品")
                .productDesc("这是一个测试商品")
                .price(new BigDecimal("1.68"))
                .build();
    }

    /**
     * 保存订单对象
     *
     * @param orderAggregate
     */
    @Override
    public void doSaveOrder(CreateOrderAggregate orderAggregate) {
        String userId = orderAggregate.getUserId();
        ProductEntity productEntity = orderAggregate.getProductEntity();
        OrderEntity orderEntity = orderAggregate.getOrderEntity();

        PayOrder order = new PayOrder();
        order.setUserId(userId);
        order.setSkuId(productEntity.getProductId());
        order.setProductName(productEntity.getProductName());
        order.setOrderId(orderEntity.getOrderId());
        order.setOrderTime(orderEntity.getOrderTime());
        order.setTotalAmount(productEntity.getPrice());
        order.setStatus(orderEntity.getOrderStatus().getCode());

        orderMapper.insert(order);
    }

    /**
     * 订单支付成功
     *
     * @param commandPay
     */
    @Override
    public boolean orderPaySuccess(CommandPay commandPay) {
        // todo 如需要其它参数可以再此地方进行构造所需要的参数
        int count = orderMapper.orderPaySuccess(commandPay);
        return count == 1;
    }

    @Override
    public boolean orderPayFail(CommandPay commandPay) {
        // todo 如需要其它参数可以再此地方进行构造所需要的参数
        int count = orderMapper.orderPayFail(commandPay);
        return count == 1;
    }
}
