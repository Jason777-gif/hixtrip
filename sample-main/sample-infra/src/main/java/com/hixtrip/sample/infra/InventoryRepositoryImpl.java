package com.hixtrip.sample.infra;

import com.hixtrip.sample.domain.inventory.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * infra层是domain定义的接口具体的实现
 */
@Component
public class InventoryRepositoryImpl implements InventoryRepository {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    @Override
    public int getInventory(String skuId) {
        // 假设这里已经获取到了库存数量，并且已经放到redis中了，假设默认库存数量为100
        String cacheKey = "inventory_count_key_" + skuId;
        Integer quantity = (Integer) redisTemplate.opsForValue().get(cacheKey);
        if (quantity == null) {
            // 缓存中没有值，调用setInventory方法设置值
            quantity = 100; // 假设默认库存数量为100
            setInventory(skuId, quantity);
        }
        return quantity;
    }

    /**
     * 设置库存值到redis中
     * @param skuId
     * @param quantity
     */
    public void setInventory(String skuId, int quantity) {
        String cacheKey = "inventory_count_key_" + skuId;
        redisTemplate.opsForValue().set(cacheKey, quantity);
    }

    @Override
    public Boolean changeInventory(String skuId, Long sellableQuantity, Long withholdingQuantity, Long occupiedQuantity) {
        // 1. 通过  redis.call('get', KEYS[1])  获取缓存中的可售库存数量（sellable）、预占库存数量（withholding）和占用库存数量（occupied）。
        // 2. 判断传入的扣减数量是否小于等于可售库存、预占库存和占用库存，如果满足条件则进行扣减操作。
        // 3. 如果满足条件，分别对可售库存、预占库存和占用库存进行相应的增减操作。
        // 4. 返回操作是否成功的布尔值。
        String cacheKey = "inventory_count_key_" + skuId;
        RedisScript<Boolean> script = new DefaultRedisScript<>(
                "local sellable = tonumber(redis.call('get', KEYS[1])) " +
                        "local withholding = tonumber(redis.call('get', KEYS[2])) " +
                        "local occupied = tonumber(redis.call('get', KEYS[3])) " +
                        "if (sellable >= tonumber(ARGV[1]) and withholding >= tonumber(ARGV[2]) and occupied >= tonumber(ARGV[3])) then " +
                        "redis.call('set', KEYS[1], sellable - tonumber(ARGV[1])) " +
                        "redis.call('set', KEYS[2], withholding - tonumber(ARGV[2])) " +
                        "redis.call('set', KEYS[3], occupied + tonumber(ARGV[3])) " +
                        "return true " +
                        "else " +
                        "return false " +
                        "end",
                Boolean.class);

        Boolean result = redisTemplate.execute(script, Collections.singletonList(cacheKey), sellableQuantity, withholdingQuantity, occupiedQuantity);

        return result != null ? result : false;
    }
}
