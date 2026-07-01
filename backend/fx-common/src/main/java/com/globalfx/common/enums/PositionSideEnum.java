package com.globalfx.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PositionSideEnum {
    LONG(1, "多头"), SHORT(2, "空头");
    private final int code;
    private final String description;
    public static PositionSideEnum of(int code) {
        for (PositionSideEnum e : values()) { if (e.code == code) return e; }
        throw new IllegalArgumentException("Unknown position side: " + code);
    }
}
