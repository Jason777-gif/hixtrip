package com.hixtrip.sample.entry.job;


import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 检测未接收到或未正确处理的支付回调通知
 * @author clw
 * @date 2024-03-07
 */
@Slf4j
@Component()
public class NoPayNotifyOrderJob {
    @Scheduled(cron = "0/3 * * * * ?")
    public void exec() {
        // todo 没做具体实现提供思路
        // 1. 查询有效期内，未接收到支付回调的订单
        // 2. 判断订单支付状态是否需要更新，有就更新没有直接返回
        // 3. 更新成功发布消息到mq
    }
}
