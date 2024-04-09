package com.hixtrip.sample.infra.db.dataobject;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * DO示例
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName(value = "inventory", autoResultMap = true)
@SuperBuilder(toBuilder = true)
public class InventoryDO {

    /**
     * 主键
     */
    @TableId
    private String id;
    /**
     * 库存
     */
    private Long inventory;
}
