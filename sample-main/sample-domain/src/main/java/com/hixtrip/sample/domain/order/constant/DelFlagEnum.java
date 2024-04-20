package com.hixtrip.sample.domain.order.constant;

/**
 * @Description: 逻辑删除
 * @version v1.0
 * @author chenzx
 * @date 2024/4/19
 */
public enum DelFlagEnum {

    NORMAL(0L, "正常"),
    DELETED(1L, "已删除");

    private Long code;

    private String name;

    DelFlagEnum(Long code, String name) {
        this.code = code;
        this.name = name;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
