package com.hixtrip.sample.app.service;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.fasterxml.jackson.databind.util.BeanUtil;
import com.hixtrip.sample.app.api.OrderService;
import com.hixtrip.sample.client.order.dto.CommandOderCreateDTO;
import com.hixtrip.sample.client.order.dto.CommandPayDTO;
import com.hixtrip.sample.domain.commodity.CommodityDomainService;
import com.hixtrip.sample.domain.inventory.InventoryDomainService;
import com.hixtrip.sample.domain.order.OrderDomainService;
import com.hixtrip.sample.domain.order.constant.DelFlagEnum;
import com.hixtrip.sample.domain.order.constant.PayStatusConstant;
import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * app层负责处理request请求，调用领域服务
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {
    //商品领域服务
    private final CommodityDomainService commodityDomainService;
    //库存领域服务
    private final InventoryDomainService inventoryDomainService;
    //订单领域服务
    private final OrderDomainService orderDomainService;


    /**
     * @Description: 生成订单
     * @param commandOderCreateDTO
     * @return: String
     * @version v1.0
     * @author chenzx
     * @date 2024/4/19
     */
    @Override
    public String createOrder(CommandOderCreateDTO commandOderCreateDTO) {
        // 1.基本信息校验
        String skuId = commandOderCreateDTO.getSkuId();
        Integer amount = commandOderCreateDTO.getAmount();
        String userid = commandOderCreateDTO.getUserId();
        if (StringUtils.isBlank(userid)){
            throw new RuntimeException("请先登录后进行操作!");
        }
        if (StringUtils.isBlank(skuId)){
            throw new RuntimeException("skuId不能为空!");
        }
        if (amount == null || Objects.equals(amount, 0)){
            throw new RuntimeException("订单数量不能为空!");
        }

        // 1.2.获取Sku库存
        Integer inventory = inventoryDomainService.getInventory(skuId);
        if(amount > inventory){
            log.error("SkuId["+skuId+"]的商品,库存不足！");
            throw new RuntimeException("库存不足！");
        }
        // 1.3.获取Sku价格、购买数量计算商品总价：totalFee
        BigDecimal skuPrice = commodityDomainService.getSkuPrice(skuId);
        if (skuPrice == null){
            throw new RuntimeException("商品不存在!");
        }
        BigDecimal total = skuPrice.multiply(new BigDecimal(amount));
        // 1.1订单数据
        Order order = new Order();
        order.setUserId(userid);
        order.setSkuId(skuId);
        order.setAmount(amount);
        order.setMoney(total);
        //未支付
        order.setPayStatus(PayStatusConstant.PAY_STATUS_WAIT_STATUS);
        order.setDelFlag(DelFlagEnum.NORMAL.getCode());
        order.setCreateBy(userid);
        order.setCreateTime(LocalDateTime.now());
        order.setUpdateBy(userid);
        order.setUpdateTime(LocalDateTime.now());
        // 1.6.将Order写入数据库order表中
        orderDomainService.createOrder(order);

        // 2.扣减库存
        try {
            Boolean isSuccess =  inventoryDomainService.changeInventory(skuId,inventory.longValue(),amount.longValue(),inventory.longValue()-amount.longValue());
            if (!isSuccess){
                throw new RuntimeException("商品过于火爆,请稍候重试!");
            }
        } catch (Exception e) {
            throw new RuntimeException("库存不足！");
        }
        return order.getId();
    }

    /**
     * @Description: 需要使用策略模式处理至少三种场景：支付成功、支付失败、重复支付(自行设计回调报文进行重复判定)
     * @param commandPayDTO
     * @return: String
     * @version v1.0
     * @author chenzx
     * @date 2024/4/19
     */
    @Override
    public String payCallback(CommandPayDTO commandPayDTO) {
        if (StringUtils.isBlank(commandPayDTO.getOrderId())){
            throw new RuntimeException("订单id不能为空!");
        }
        if (StringUtils.isBlank(commandPayDTO.getPayStatus())){
            throw new RuntimeException("payStatus不能为空!");
        }
        CommandPay commandPay = new CommandPay();
        BeanUtils.copyProperties(commandPayDTO, commandPay);
        if(commandPayDTO.getPayStatus().equals(PayStatusConstant.PAY_STATUS_SUCCEED_STATUS)){
            return orderDomainService.orderPaySuccess(commandPay);
        }else{
            return orderDomainService.orderPayFail(commandPay);
        }
    }
}
