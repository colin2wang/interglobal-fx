package com.globalfx.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TimeInForceEnum {
    GTC(1, "永久"), GFD(2, "当日有效"), GTD(3, "指定日期");
    private final int code;
    private final String description;
    public static TimeInForceEnum of(int code) {
        for (TimeInForceEnum e : values()) { if (e.code == code) return e; }
        throw new IllegalArgumentException("Unknown time in force: " + code);
    }
}
