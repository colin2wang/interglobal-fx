package com.globalfx.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderStatusEnum {

    PENDING(1, "挂单中"),
    PARTIAL_FILLED(2, "部分成交"),
    FILLED(3, "完全成交"),
    CANCELLED(4, "已取消"),
    REJECTED(5, "已拒绝"),
    EXPIRED(6, "已过期");

    private final int code;
    private final String description;

    public static OrderStatusEnum of(int code) {
        for (OrderStatusEnum e : values()) {
            if (e.code == code) return e;
        }
        throw new IllegalArgumentException("Unknown order status: " + code);
    }
}
