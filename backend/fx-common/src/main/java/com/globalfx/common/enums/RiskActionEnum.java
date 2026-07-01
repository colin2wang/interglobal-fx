package com.globalfx.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RiskActionEnum {
    REJECT(1, "拒绝"), WARN(2, "警告"), DELAY(3, "延迟"), MANUAL_REVIEW(4, "人工审核");
    private final int code;
    private final String description;
    public static RiskActionEnum of(int code) {
        for (RiskActionEnum e : values()) { if (e.code == code) return e; }
        throw new IllegalArgumentException("Unknown risk action: " + code);
    }
}
