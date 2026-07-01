package com.globalfx.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderTypeEnum {

    MARKET(1, "市价单"),
    LIMIT(2, "限价单"),
    STOP(3, "止损单"),
    TAKE_PROFIT(4, "止盈单"),
    STOP_LIMIT(5, "止损限价单"),
    PENDING(6, "挂单");

    private final int code;
    private final String description;

    public static OrderTypeEnum of(int code) {
        for (OrderTypeEnum e : values()) {
            if (e.code == code) return e;
        }
        throw new IllegalArgumentException("Unknown order type: " + code);
    }
}
