package com.globalfx.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SymbolTypeEnum {
    MAJOR(1, "主要货币对"), MINOR(2, "次要货币对"), EXOTIC(3, "Exotic货币对"),
    METAL(4, "贵金属"), INDEX(5, "指数"), CRYPTO(6, "加密货币");
    private final int code;
    private final String description;
    public static SymbolTypeEnum of(int code) {
        for (SymbolTypeEnum e : values()) { if (e.code == code) return e; }
        throw new IllegalArgumentException("Unknown symbol type: " + code);
    }
}
