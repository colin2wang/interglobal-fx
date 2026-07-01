package com.globalfx.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.globalfx.system.dto.PermissionDTO;
import com.globalfx.system.entity.Permission;
import com.globalfx.system.vo.PermissionTreeVO;

import java.util.List;

public interface PermissionService extends IService<Permission> {

    List<PermissionTreeVO> getPermissionTree();

    void createPermission(PermissionDTO dto);

    void updatePermission(PermissionDTO dto);

    void deletePermission(Long id);
}
