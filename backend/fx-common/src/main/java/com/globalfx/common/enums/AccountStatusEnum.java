package com.globalfx.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AccountStatusEnum {
    ACTIVE(1, "正常"), FROZEN(2, "冻结"), CLOSED(3, "关闭"), FORCE_CLOSING(4, "强平中");
    private final int code;
    private final String description;
    public static AccountStatusEnum of(int code) {
        for (AccountStatusEnum e : values()) { if (e.code == code) return e; }
        throw new IllegalArgumentException("Unknown account status: " + code);
    }
}
