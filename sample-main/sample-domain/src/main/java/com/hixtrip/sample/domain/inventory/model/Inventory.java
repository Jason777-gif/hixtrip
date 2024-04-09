package com.hixtrip.sample.domain.inventory.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xgh 2024/4/8
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {
    /**
     * id
     */
    private String skuId;
    /**
     * 库存数
     */
    private long inventory;

    public void deduct(long need) {
        if (need > inventory) {
            throw new RuntimeException("存储不足");
        }
        this.inventory -= need;
    }
}
