package com.globalfx.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.globalfx.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("usr_permission")
public class Permission extends BaseEntity {

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
    private Long tenantId;
}
