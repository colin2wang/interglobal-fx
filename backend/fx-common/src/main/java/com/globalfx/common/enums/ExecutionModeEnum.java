package com.globalfx.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExecutionModeEnum {
    A_BOOK(1, "A-Book"), B_BOOK(2, "B-Book"), HYBRID(3, "Hybrid");
    private final int code;
    private final String description;
    public static ExecutionModeEnum of(int code) {
        for (ExecutionModeEnum e : values()) { if (e.code == code) return e; }
        throw new IllegalArgumentException("Unknown execution mode: " + code);
    }
}
