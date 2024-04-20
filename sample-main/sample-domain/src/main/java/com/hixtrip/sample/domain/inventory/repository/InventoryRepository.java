package com.hixtrip.sample.domain.inventory.repository;

/**
 * @Description: 库存领域服务
 * @version v1.0
 * @author chenzx
 * @date 2024/4/19
 */
public interface InventoryRepository {


    /**
     * @Description: 获取库存
     * @version v1.0
     * @author chenzx
     * @date 2024/4/19
     */
    Integer getInventory(String skuId);
    /**
     * 修改库存
     *
     * @param skuId
     * @param sellableQuantity    可售库存
     * @param withholdingQuantity 预占库存
     * @param occupiedQuantity    占用库存
     * @return
     */
    Boolean changeInventory(String skuId, Long sellableQuantity, Long withholdingQuantity, Long occupiedQuantity);
}
