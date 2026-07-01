package com.globalfx.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TicketCategoryEnum {
    TRADING(1, "交易问题"), WITHDRAWAL(2, "出金问题"), ACCOUNT(3, "账户问题"),
    SYSTEM(4, "系统问题"), COMPLAINT(5, "投诉"), SUGGESTION(6, "建议"), OTHER(7, "其他");
    private final int code;
    private final String description;
    public static TicketCategoryEnum of(int code) {
        for (TicketCategoryEnum e : values()) { if (e.code == code) return e; }
        throw new IllegalArgumentException("Unknown ticket category: " + code);
    }
}
