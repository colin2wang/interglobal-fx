package com.globalfx.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RiskLevelEnum {
    RETAIL(1, "零售客户"), PROFESSIONAL(2, "专业客户"), INSTITUTIONAL(3, "机构客户");
    private final int code;
    private final String description;
    public static RiskLevelEnum of(int code) {
        for (RiskLevelEnum e : values()) { if (e.code == code) return e; }
        throw new IllegalArgumentException("Unknown risk level: " + code);
    }
}
