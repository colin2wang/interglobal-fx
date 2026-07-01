package com.globalfx.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.globalfx.common.exception.BusinessException;
import com.globalfx.common.result.ResultCode;
import com.globalfx.system.dto.RoleDTO;
import com.globalfx.system.entity.Role;
import com.globalfx.system.entity.RolePermission;
import com.globalfx.system.mapper.RoleMapper;
import com.globalfx.system.mapper.RolePermissionMapper;
import com.globalfx.system.service.RoleService;
import com.globalfx.system.vo.RoleVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    private final RoleMapper roleMapper;
    private final RolePermissionMapper rolePermissionMapper;

    @Override
    public List<RoleVO> listAll(Integer status) {
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(status != null, Role::getStatus, status)
                .orderByAsc(Role::getSortOrder);
        List<Role> roles = roleMapper.selectList(wrapper);
        return roles.stream().map(role -> {
            RoleVO vo = new RoleVO();
            BeanUtils.copyProperties(role, vo);
            return vo;
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createRole(RoleDTO dto) {
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Role::getRoleCode, dto.getRoleCode());
        if (roleMapper.selectCount(wrapper) > 0) {
            throw new BusinessException(ResultCode.DATA_CONFLICT, "角色编码已存在");
        }

        Role role = new Role();
        BeanUtils.copyProperties(dto, role);
        role.setStatus(dto.getStatus() != null ? dto.getStatus() : 1);
        roleMapper.insert(role);

        saveRolePermissions(role.getId(), dto.getPermissionIds(), role.getTenantId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateRole(RoleDTO dto) {
        Role existing = roleMapper.selectById(dto.getId());
        if (existing == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "角色不存在");
        }

        Role role = new Role();
        BeanUtils.copyProperties(dto, role);
        roleMapper.updateById(role);

        if (dto.getPermissionIds() != null) {
            rolePermissionMapper.deleteByRoleId(role.getId());
            saveRolePermissions(role.getId(), dto.getPermissionIds(), existing.getTenantId());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRole(Long id) {
        roleMapper.deleteById(id);
        rolePermissionMapper.deleteByRoleId(id);
    }

    @Override
    public List<Long> getPermissionIds(Long roleId) {
        return roleMapper.selectPermissionIdsByRoleId(roleId);
    }

    private void saveRolePermissions(Long roleId, List<Long> permissionIds, Long tenantId) {
        if (CollectionUtils.isEmpty(permissionIds)) {
            return;
        }
        for (Long permissionId : permissionIds) {
            RolePermission rp = new RolePermission();
            rp.setRoleId(roleId);
            rp.setPermissionId(permissionId);
            rp.setTenantId(tenantId);
            rolePermissionMapper.insert(rp);
        }
    }
}
