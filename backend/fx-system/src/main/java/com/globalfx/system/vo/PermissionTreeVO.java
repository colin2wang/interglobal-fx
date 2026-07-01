package com.globalfx.system.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PermissionTreeVO {

    private Long id;
    private Long parentId;
    private String permissionCode;
    private String permissionName;
    private Integer permissionType;
    private String path;
    private String component;
    private String icon;
    private Integer sortOrder;
    private Integer visible;
    private Integer status;
    private List<PermissionTreeVO> children = new ArrayList<>();
}
