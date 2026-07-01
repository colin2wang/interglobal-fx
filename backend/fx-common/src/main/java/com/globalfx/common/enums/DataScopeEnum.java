package com.globalfx.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DataScopeEnum {
    ALL(1, "全部数据"), TENANT(2, "本租户数据"), DEPARTMENT(3, "本部门数据"), SELF(4, "仅本人数据");
    private final int code;
    private final String description;
}
