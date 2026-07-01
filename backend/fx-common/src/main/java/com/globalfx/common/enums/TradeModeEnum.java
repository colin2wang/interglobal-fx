package com.globalfx.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TradeModeEnum {
    TRADEABLE(1, "可交易"), VIEW_ONLY(2, "仅查看"), DISABLED(3, "停用");
    private final int code;
    private final String description;
    public static TradeModeEnum of(int code) {
        for (TradeModeEnum e : values()) { if (e.code == code) return e; }
        throw new IllegalArgumentException("Unknown trade mode: " + code);
    }
}
