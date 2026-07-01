package com.globalfx.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.globalfx.system.dto.RoleDTO;
import com.globalfx.system.entity.Role;
import com.globalfx.system.vo.RoleVO;

import java.util.List;

public interface RoleService extends IService<Role> {

    List<RoleVO> listAll(Integer status);

    void createRole(RoleDTO dto);

    void updateRole(RoleDTO dto);

    void deleteRole(Long id);

    List<Long> getPermissionIds(Long roleId);
}
