package com.hixtrip.sample.infra;

import com.hixtrip.sample.domain.inventory.model.Inventory;
import com.hixtrip.sample.domain.inventory.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * infra层是domain定义的接口具体的实现
 */
@Component
public class InventoryRepositoryImpl implements InventoryRepository {
    final String key = "inventory";
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    @Override
    public Inventory getInventory(String skuId) {
        Assert.hasText(skuId, "skuId不能为空");
        Object inventory = redisTemplate.opsForHash().get(key, skuId);
        Assert.notNull(inventory, "id:" + skuId + "查不到库存");
        return new Inventory(skuId, Long.parseLong(String.valueOf(inventory)));
    }

    @Override
    public void deductInventory(String skuId, int amount) {
        Assert.isTrue(amount > 0, "amount 需大于0");
        redisTemplate.opsForHash().increment(key, skuId, -amount);
    }
}
