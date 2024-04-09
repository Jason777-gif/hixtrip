package com.hixtrip.sample.infra.db.dataobject;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName(value = "order", autoResultMap = true)
@SuperBuilder(toBuilder = true)
public class OrderDO {

    /**
     * 订单号
     */
    private String id;
    /**
     * 购买人
     */
    private String userId;
    /**
     * SkuId
     */
    private String skuId;
    /**
     * 购买数量
     */
    private Integer amount;
    /**
     * 购买金额
     */
    private Double money;
    /**
     * 购买时间
     */
    private Date payTime;
    /**
     * 支付状态（0代表待支付 1代表支付成功 2代表支付失败）
     */
    private String payStatus;
    /**
     * 删除标志（0代表存在 1代表删除）
     */
    private Integer delFlag;
    /**
     * 创建人
     */
    private String createBy;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改人
     */
    private String updateBy;
    /**
     * 修改时间
     */
    private Date updateTime;

}

