package com.globalfx.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum KycStatusEnum {
    NOT_SUBMITTED(0, "未提交"), PENDING(1, "待审核"), REVIEWING(2, "审核中"), APPROVED(3, "已通过"), REJECTED(4, "已拒绝");
    private final int code;
    private final String description;
    public static KycStatusEnum of(int code) {
        for (KycStatusEnum e : values()) { if (e.code == code) return e; }
        throw new IllegalArgumentException("Unknown KYC status: " + code);
    }
}
