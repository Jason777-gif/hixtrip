package com.hixtrip.sample.app.service;
import java.time.LocalDateTime;

import com.hixtrip.sample.app.api.OrderService;
import com.hixtrip.sample.app.service.callback.ICallBackHandler;
import com.hixtrip.sample.client.order.dto.CommandOderCreateDTO;
import com.hixtrip.sample.client.order.dto.CommandPayDTO;
import com.hixtrip.sample.domain.commodity.CommodityDomainService;
import com.hixtrip.sample.domain.inventory.InventoryDomainService;
import com.hixtrip.sample.domain.order.OrderDomainService;
import com.hixtrip.sample.domain.order.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * app层负责处理request请求，调用领域服务
 */
@Component
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderDomainService orderDomainService;
    @Autowired
    InventoryDomainService inventoryDomainService;
    @Autowired
    CommodityDomainService commodityDomainService;
    @Autowired
    List<ICallBackHandler> callBackHandlers;

    /**
     *      *
     * @param dto
     * @return
     */
    @Override
    public String createOrder(CommandOderCreateDTO dto) {
        inventoryDomainService.deductInventory(dto.getSkuId(), dto.getAmount());
        BigDecimal price = commodityDomainService.getSkuPrice(dto.getSkuId());
        Order order = new Order();
        order.setId(UUID.randomUUID().toString());
        order.setUserId(dto.getUserId());
        order.setSkuId(dto.getSkuId());
        order.setAmount(dto.getAmount());
        order.setMoney(price.multiply(BigDecimal.valueOf(dto.getAmount())));
        order.setDelFlag(0L);
        order.setPayStatus("UNPAID");
        order.setCreateBy(dto.getUserId());
        order.setCreateTime(LocalDateTime.now());
        order.setUpdateBy(dto.getUserId());
        order.setUpdateTime(LocalDateTime.now());

        orderDomainService.createOrder(order);
        return "200";
    }

    @Override
    public String callback(CommandPayDTO dto) {
        callBackHandlers.stream()
                .filter(handle->handle.canHandle(dto))
                .forEach(handle->handle.doHandle(dto));
        return "200";
    }
}
