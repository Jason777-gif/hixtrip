package com.hixtrip.sample.infra.db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import com.hixtrip.sample.infra.db.dataobject.SampleDO;
import com.hixtrip.sample.infra.po.PayOrder;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper {

    PayOrder queryUnPayOrder(PayOrder orderReq);

    void updateOrderPayInfo(PayOrder order);

    void insert(PayOrder order);

    int orderPaySuccess(CommandPay commandPay);

    int orderPayFail(CommandPay commandPay);
}
