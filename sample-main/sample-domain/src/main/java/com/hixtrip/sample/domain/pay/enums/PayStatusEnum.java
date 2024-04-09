package com.hixtrip.sample.domain.pay.enums;

import lombok.Getter;

@Getter
public enum PayStatusEnum {

    PENDING_PAY("pending_pay", "待支付"),
    PAID_SUCCESS("paid_success", "支付成功"),
    PAID_REPEATED("paid_repeated", "重复支付"),
    PAID_FAIL("paid_fail", "支付失败");

    public String code;

    public String desc;

    PayStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static PayStatusEnum getByCode(String codeVal) {
        for (PayStatusEnum resultCodeEnum : PayStatusEnum.values()) {
            if (resultCodeEnum.code.equals(codeVal)) {
                return resultCodeEnum;
            }
        }
        return null;
    }

}
