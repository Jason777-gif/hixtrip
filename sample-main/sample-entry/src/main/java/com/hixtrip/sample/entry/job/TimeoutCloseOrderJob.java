package com.hixtrip.sample.entry.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 超时关单任务
 * @author clw
 * @date 2024-03-07
 */
@Slf4j
@Component()
public class TimeoutCloseOrderJob {

    @Scheduled(cron = "0 0/10 * * * ?")
    public void exec() {
        // todo 查询超时15分钟，未支付订单,执行关单操作,如微信要调用第三方api
        //  https://api.mch.weixin.qq.com/v3/pay/transactions/out-trade-no/{out_trade_no}/close
    }
}
