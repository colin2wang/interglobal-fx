package com.globalfx.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PermissionTypeEnum {
    DIRECTORY(1, "目录"), MENU(2, "菜单"), BUTTON(3, "按钮"), API(4, "接口");
    private final int code;
    private final String description;
}
