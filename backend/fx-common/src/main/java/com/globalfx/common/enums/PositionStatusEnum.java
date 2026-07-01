package com.globalfx.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PositionStatusEnum {
    OPEN(1, "持仓中"), CLOSED(2, "已平仓"), PARTIAL_CLOSED(3, "部分平仓"), FORCE_CLOSING(4, "强平中");
    private final int code;
    private final String description;
    public static PositionStatusEnum of(int code) {
        for (PositionStatusEnum e : values()) { if (e.code == code) return e; }
        throw new IllegalArgumentException("Unknown position status: " + code);
    }
}
