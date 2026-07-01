package com.globalfx.system.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class RoleDTO {

    private Long id;

    @NotBlank(message = "角色编码不能为空")
    private String roleCode;

    @NotBlank(message = "角色名称不能为空")
    private String roleName;

    private Integer dataScope;
    private Integer status;
    private Integer sortOrder;
    private List<Long> permissionIds;
    private String remark;
}
