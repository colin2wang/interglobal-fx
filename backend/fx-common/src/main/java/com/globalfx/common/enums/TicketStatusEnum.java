package com.globalfx.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TicketStatusEnum {
    PENDING_ASSIGNMENT(1, "待分配"), IN_PROGRESS(2, "处理中"), AWAITING_REPLY(3, "待回复"),
    RESOLVED(4, "已解决"), CLOSED(5, "已关闭");
    private final int code;
    private final String description;
    public static TicketStatusEnum of(int code) {
        for (TicketStatusEnum e : values()) { if (e.code == code) return e; }
        throw new IllegalArgumentException("Unknown ticket status: " + code);
    }
}
