package com.globalfx.system.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RoleVO {

    private Long id;
    private String roleCode;
    private String roleName;
    private Integer dataScope;
    private Integer status;
    private Integer sortOrder;
    private LocalDateTime createdTime;
    private String remark;
}
