package com.hixtrip.sample.infra;

import com.hixtrip.sample.domain.inventory.repository.InventoryRepository;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * infra层是domain定义的接口具体的实现
 */
@Component
public class InventoryRepositoryImpl implements InventoryRepository {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private RedissonClient redissonClient;

    /**
     * sku对应库存key,格式   INVENTORY:{skuId}
     */
    public static final String INVENTORY_KEY = "INVENTORY:";
    /**
     * sku对应库存锁key,格式   INVENTORY:{skuId}
     */
    public static final String LOCK_INVENTORY_KEY = "LOCK:INVENTORY:";

    @Override
    public Integer getInventory(String skuId) {
        Integer inventory = (Integer) redisTemplate.opsForValue().get(INVENTORY_KEY+skuId);
        return inventory == null ? 0 :inventory ;
    }

    @Override
    public Boolean changeInventory(String skuId, Long sellableQuantity, Long withholdingQuantity, Long occupiedQuantity) {
        RLock lock = redissonClient.getLock(LOCK_INVENTORY_KEY + skuId);
        try{
            boolean isLock = lock.tryLock(10, TimeUnit.SECONDS);
            if (!isLock){
                return false;
            }
            Integer inventory = (Integer) redisTemplate.opsForValue().get(INVENTORY_KEY+skuId);
            if (inventory < withholdingQuantity){
                throw new RuntimeException("库存不足！");
            }
            redisTemplate.opsForValue().set(INVENTORY_KEY+skuId,occupiedQuantity.intValue());
            return true;
        }catch (Exception e){
            throw new RuntimeException("扣减库存异常！");
        }finally {
            lock.unlock();
        }
    }

}
