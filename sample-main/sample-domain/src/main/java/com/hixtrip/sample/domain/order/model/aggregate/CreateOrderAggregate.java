package com.hixtrip.sample.domain.order.model.aggregate;

import com.hixtrip.sample.domain.order.model.entity.OrderEntity;
import com.hixtrip.sample.domain.order.model.entity.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderAggregate {
    /** 用户ID */
    private String userId;
    /** 用户ID */
    private ProductEntity productEntity;

    private OrderEntity orderEntity;
}
