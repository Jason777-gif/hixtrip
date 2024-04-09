package com.hixtrip.sample.domain.inventory;

import com.hixtrip.sample.domain.inventory.model.Inventory;
import com.hixtrip.sample.domain.inventory.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 库存领域服务
 * 库存设计，忽略仓库、库存品、计量单位等业务
 */
@Component
public class InventoryDomainService {
    @Autowired
    InventoryRepository inventoryRepository;

    /**
     * 获取sku当前库存
     *
     * @param skuId
     */
    public Long getInventory(String skuId) {
        return inventoryRepository.getInventory(skuId).getInventory();
    }

    /**
     * 获取sku减少库存
     * 单机使用jvm锁，多实例用分布式锁
     * @param skuId
     * @param amount
     */
    public void deductInventory(String skuId, int amount) {
        Inventory inventory = inventoryRepository.getInventory(skuId);
        inventory.deduct(amount);
        inventoryRepository.deductInventory(skuId,amount);
    }

    /**
     * 修改库存
     *
     * @param skuId
     * @param sellableQuantity    可售库存
     * @param withholdingQuantity 预占库存
     * @param occupiedQuantity    占用库存
     * @return
     */
    public Boolean changeInventory(String skuId, Long sellableQuantity, Long withholdingQuantity, Long occupiedQuantity) {
        //todo 需要你在infra实现，只需要实现缓存操作。
        // 在库存这种业务下，直接对库存进行设置有些不合理，应该修改成对库存进行新增或减少
        return null;
    }
}
