package com.hixtrip.sample.domain.order;

import com.hixtrip.sample.domain.commodity.CommodityDomainService;
import com.hixtrip.sample.domain.order.enums.DelFlag;
import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.order.repository.OrderRepository;
import com.hixtrip.sample.domain.pay.enums.PayStatusEnum;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * 订单领域服务
 * todo 只需要实现创建订单即可
 */
@Component
public class OrderDomainService {

    @Autowired
    private CommodityDomainService commodityDomainService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * todo 需要实现
     * 创建待付款订单
     */
    public String createOrder(Order order) {
        //需要你在infra实现, 自行定义出入参
        // 1、获取下单数量
        Integer amount = order.getAmount();
        // 2、Redis中通过Lua脚本进行库存扣减
        String key = "product:" + order.getSkuId();
        String lua = "local key = KEYS[1]" +
                "     local amount = tonumber(ARGV[1])" +
                "     -- 获取商品当前的库存量" +
                "     local stock = tonumber(redis.call('get', key))" +
                "     -- 如果库存足够，则减少库存并返回新的库存量" +
                "     if stock >= amount then" +
                "       redis.call('decrby', key, amount)" +
                "       return redis.call('get', key)" +
                "     else" +
                "       return -1" +
                "     end";
        Long result = redisTemplate.execute(new DefaultRedisScript<>(lua, Long.class),
                Arrays.asList(key), amount);
        if (result == -1) {
            return "库存不足";
        }
        // 3、下单操作
        // 3.1 模拟生成订单号
        String orderId = "000000000001";
        order.setId(orderId);
        // 3.2 计算订单金额
        BigDecimal skuPrice = commodityDomainService.getSkuPrice(order.getSkuId());
        BigDecimal total = skuPrice.multiply(new BigDecimal(amount));
        order.setMoney(total);
        // 3.3 填充支付信息
        order.setPayTime(LocalDateTime.now());
        order.setPayStatus(PayStatusEnum.PENDING_PAY.getCode());
        // 3.4 填充其他
        order.setDelFlag(DelFlag.UN_DELETED.getCode());
        order.setCreateBy(order.getUserId());
        order.setCreateTime(LocalDateTime.now());
        order.setUpdateBy(order.getUserId());
        order.setUpdateTime(LocalDateTime.now());
        // 3.5 入库
        orderRepository.createOrder(order);
        // 3.6 返回订单号
        return orderId;
    }

    /**
     * 根据订单号获取订单信息
     * @param orderId
     * @return
     */
    public Order getOrder(String orderId) {
        return orderRepository.getOrder(orderId);
    }

    /**
     * todo 需要实现
     * 待付款订单支付成功
     */
    public void orderPaySuccess(CommandPay commandPay) {
        //需要你在infra实现, 自行定义出入参
        // 1、更新订单状态为支付成功
        orderRepository.updateOrderStatus(commandPay.getOrderId(), PayStatusEnum.PAID_SUCCESS.getCode());
        // 2、TODO 同步redis库存和数据库 具体看实时性要求
        // 2.1 实时性要求高 直接数据同步更新或异步MQ更新
        // 2.2 实时性要求不高 0点定时任务更新
    }

    /**
     * todo 需要实现
     * 待付款订单支付失败
     */
    public void orderPayFail(CommandPay commandPay) {
        //需要你在infra实现, 自行定义出入参
        // 1、更新订单状态为支付失败
        orderRepository.updateOrderStatus(commandPay.getOrderId(), PayStatusEnum.PAID_FAIL.getCode());
        // 2、redis库存incr回去对应数量
        String orderId = commandPay.getOrderId();
        Order order = orderRepository.getOrder(orderId);
        redisTemplate.opsForValue().increment("product:" + orderId, ((-1) * order.getAmount()));
    }

}
