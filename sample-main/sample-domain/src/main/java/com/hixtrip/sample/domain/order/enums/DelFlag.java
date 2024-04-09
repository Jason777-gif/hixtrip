package com.hixtrip.sample.domain.order.enums;

import lombok.Getter;

@Getter
public enum DelFlag {

    DELETED(1L, "已删除"),
    UN_DELETED(0L, "未删除");

    public Long code;

    public String desc;

    DelFlag(Long code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static DelFlag getByCode(int codeVal) {
        for (DelFlag resultCodeEnum : DelFlag.values()) {
            if (resultCodeEnum.code == codeVal) {
                return resultCodeEnum;
            }
        }
        return null;
    }

}
