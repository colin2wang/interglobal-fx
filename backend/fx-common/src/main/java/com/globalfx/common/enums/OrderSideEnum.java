package com.globalfx.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderSideEnum {

    BUY(1, "买入"),
    SELL(2, "卖出");

    private final int code;
    private final String description;

    public static OrderSideEnum of(int code) {
        for (OrderSideEnum e : values()) {
            if (e.code == code) return e;
        }
        throw new IllegalArgumentException("Unknown order side: " + code);
    }
}
