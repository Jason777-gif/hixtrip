package com.hixtrip.sample.infra;

import com.hixtrip.sample.domain.order.constant.PayStatusConstant;
import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.order.repository.OrderRepository;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import com.hixtrip.sample.infra.db.convertor.OrderDOConvertor;
import com.hixtrip.sample.infra.db.dataobject.OrderDO;
import com.hixtrip.sample.infra.db.mapper.OrderMapper;
import com.hixtrip.sample.infra.db.mapper.SampleMapper;
import lombok.val;
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 订单的领域层操作具体实现
 */
@Component
public class OrderRepositoryImpl implements OrderRepository {
    /**
     * sku对应库存锁key,格式   INVENTORY:{skuId}
     */
    public static final String LOCK_ORDER_PAY_KEY = "LOCK:ORDER:PAY:";

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public void createOrder(Order order) {
        OrderDO orderDO = OrderDOConvertor.INSTANCE.domainToDo(order);
        orderMapper.insert(orderDO);
        order.setId(orderDO.getId());
    }

    @Override
    public void updateOrder(Order order) {
        OrderDO orderDO = OrderDOConvertor.INSTANCE.domainToDo(order);
        orderMapper.updateById(orderDO);
    }

    @Override
    public Order selectOrderById(String id) {
        OrderDO orderDO = orderMapper.selectById(id);
        return OrderDOConvertor.INSTANCE.doToDomain(orderDO);
    }

    @Override
    public String orderPaySuccess(CommandPay commandPay) {
        String result = orderPay(commandPay);
        return result == null ? "支付成功！": result;
    }

    @Override
    public String orderPayFail(CommandPay commandPay) {
        String result = orderPay(commandPay);
        return result == null ? "支付失败！": result;
    }

    /**
     * @Description: 订单支付更新订单
     * @param commandPay
     * @return: String
     * @version v1.0
     * @author chenzx
     * @date 2024/4/20
     */
    private String orderPay(CommandPay commandPay) {
        RReadWriteLock readWriteLock = redissonClient.getReadWriteLock(LOCK_ORDER_PAY_KEY + commandPay.getOrderId());
        RLock rLock = readWriteLock.writeLock();
        try {
            boolean isLock = rLock.tryLock(10, TimeUnit.SECONDS);
            if (!isLock) {
                return "重复支付！！";
            }
            Order order = selectOrderById(commandPay.getOrderId());
            if(order == null){
                return "订单不存在，无需支付！！";
            }
            if(order.getPayStatus().equals(PayStatusConstant.PAY_STATUS_SUCCEED_STATUS)){
                return "重复支付！！";
            }
            order.setPayStatus(commandPay.getPayStatus());
            updateOrder(order);
        }catch (Exception e){
            throw new RuntimeException("更新订单异常！");
        }finally {
            rLock.unlock();
        }
        return null;
    }

}
