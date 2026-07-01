package com.globalfx.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserStatusEnum {
    ACTIVE(1, "正常"), DISABLED(2, "停用"), LOCKED(3, "锁定");
    private final int code;
    private final String description;
    public static UserStatusEnum of(int code) {
        for (UserStatusEnum e : values()) { if (e.code == code) return e; }
        throw new IllegalArgumentException("Unknown user status: " + code);
    }
}
