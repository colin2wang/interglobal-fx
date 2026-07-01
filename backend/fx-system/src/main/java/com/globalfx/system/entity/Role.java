package com.globalfx.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.globalfx.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("usr_role")
public class Role extends BaseEntity {

    private String roleCode;
    private String roleName;
    private Long tenantId;
    private Integer dataScope;
    private Long deptId;
    private Integer status;
    private Integer sortOrder;
}
