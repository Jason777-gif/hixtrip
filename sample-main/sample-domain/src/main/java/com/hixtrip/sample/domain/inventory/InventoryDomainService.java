package com.hixtrip.sample.domain.inventory;

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
     * @param skuId
     */
    public Long getInventory(String skuId) {
        //todo 需要你在infra实现, 返回的领域对象自行定义
        return inventoryRepository.getSellableQuantity(skuId);
    }

    /**
     * 修改库存
     * @param skuId
     * @param sellableQuantity    可售库存
     * @param withholdingQuantity 预占库存
     * @param occupiedQuantity    占用库存
     * @return
     */
    public Boolean changeInventory(String skuId, Long sellableQuantity, Long withholdingQuantity, Long occupiedQuantity) {
        //todo 需要你在infra实现
        Inventory inventory = Inventory.builder()
                                       .skuId(skuId)
                                       .sellableQuantity(sellableQuantity)
                                       .withholdingQuantity(withholdingQuantity)
                                       .occupiedQuantity(occupiedQuantity)
                                       .build();
        return inventoryRepository.updateInventory(inventory) > 0;
    }
}
