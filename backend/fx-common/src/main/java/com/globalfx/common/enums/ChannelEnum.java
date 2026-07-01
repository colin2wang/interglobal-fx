package com.globalfx.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ChannelEnum {
    WEB(1, "Web端"), APP(2, "App端"), API(3, "API接口"),
    MT4(4, "MT4"), MT5(5, "MT5"), FIX(6, "FIX协议");
    private final int code;
    private final String description;
    public static ChannelEnum of(int code) {
        for (ChannelEnum e : values()) { if (e.code == code) return e; }
        throw new IllegalArgumentException("Unknown channel: " + code);
    }
}
