package com.globalfx.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CustomerStatusEnum {
    POTENTIAL(1, "潜在"), ACTIVE(2, "活跃"), SILENT(3, "沉默"), LOST(4, "流失");
    private final int code;
    private final String description;
    public static CustomerStatusEnum of(int code) {
        for (CustomerStatusEnum e : values()) { if (e.code == code) return e; }
        throw new IllegalArgumentException("Unknown customer status: " + code);
    }
}
