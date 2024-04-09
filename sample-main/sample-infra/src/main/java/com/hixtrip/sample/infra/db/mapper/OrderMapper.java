package com.hixtrip.sample.infra.db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hixtrip.sample.infra.db.dataobject.OrderDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * mapper示例
 */
@Mapper
public interface OrderMapper extends BaseMapper<OrderDO> {

    /**
     * 更新订单状态
     * @param orderId
     * @param code
     */
    void updateOrderStatus(@Param("orderId") String orderId, @Param("code")String code);

}
