package com.hixtrip.sample.entry.job;


import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 订单补货任务
 * @author clw
 * @date 2024-03-07
 */
@Slf4j
@Component()
public class OrderReplenishmentJob {
    /**
     * 执行订单补货，超时n分钟，已支付，待发货未发货的订单
     */
    @Scheduled(cron = "0 0/10 * * * ?")
    public void exec() {
        // todo 没做具体实现提供思路
        // 1. 查询待补货订单
        // 2. 判断订单补货是否存在，没有直接返回，有则发布消息到mq

    }
}
