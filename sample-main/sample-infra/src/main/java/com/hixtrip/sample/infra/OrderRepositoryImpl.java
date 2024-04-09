package com.hixtrip.sample.infra;

import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.order.repository.OrderRepository;
import com.hixtrip.sample.infra.db.convertor.OrderDOConvertor;
import com.hixtrip.sample.infra.db.dataobject.OrderDO;
import com.hixtrip.sample.infra.db.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;


@Repository
public class OrderRepositoryImpl implements OrderRepository {
    final static OrderDOConvertor CONVERTOR = OrderDOConvertor.INSTANCE;
    @Autowired
    private OrderMapper orderMapper;


    @Override
    public Order findById(String id) {
        Assert.hasText(id, "id不能为空");
        OrderDO orderDO = orderMapper.selectById(id);
        Assert.notNull(orderDO, "id：" + id + "，查不到订单");
        return CONVERTOR.convert(orderDO);
    }

    @Override
    public int createOrder(Order order) {
        Assert.notNull(order, "order不能为空");
        OrderDO orderDO = CONVERTOR.convert(order);
        return orderMapper.insert(orderDO);
    }

    @Override
    public int updateStatus(Order order) {
        return orderMapper.updateStatus(CONVERTOR.convert(order));
    }
}
