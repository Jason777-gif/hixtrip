package com.hixtrip.sample.domain.order.repository;

import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.pay.model.CommandPay;

/**
 *订单领域层定义接口，基础设施层实现具体查询方式，如查数据库、缓存、调用第三方SDK等
 */
public interface OrderRepository {

    /**
     * @Description: 创建待付款订单
     * @param order
     * @version v1.0
     * @author chenzx
     * @date 2024/4/19
     */
    void createOrder(Order order);

    /**
     * @Description: 通过id获取订单信息
     * @param id
     * @return: Order
     * @version v1.0
     * @author chenzx
     * @date 2024/4/19
     */
    Order selectOrderById(String id);


    /**
     * @Description: 待付款订单支付成功
     * @param commandPay
     * @version v1.0
     * @author chenzx
     * @date 2024/4/19
     */
    String orderPaySuccess(CommandPay commandPay);


    /**
     * @Description: 待付款订单支付失败
     * @param commandPay
     * @version v1.0
     * @author chenzx
     * @date 2024/4/19
     */
    String orderPayFail(CommandPay commandPay);

    /**
     * 更新订单数据
     */
    void updateOrder(Order order);
}
