package com.globalfx.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AccountTypeEnum {
    REAL(1, "真实账户"), DEMO(2, "模拟账户"), VIP(3, "VIP账户");
    private final int code;
    private final String description;
    public static AccountTypeEnum of(int code) {
        for (AccountTypeEnum e : values()) { if (e.code == code) return e; }
        throw new IllegalArgumentException("Unknown account type: " + code);
    }
}
