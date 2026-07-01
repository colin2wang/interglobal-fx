package com.globalfx.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.globalfx.common.exception.BusinessException;
import com.globalfx.common.result.ResultCode;
import com.globalfx.system.dto.PermissionDTO;
import com.globalfx.system.entity.Permission;
import com.globalfx.system.mapper.PermissionMapper;
import com.globalfx.system.service.PermissionService;
import com.globalfx.system.vo.PermissionTreeVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    private final PermissionMapper permissionMapper;

    @Override
    public List<PermissionTreeVO> getPermissionTree() {
        LambdaQueryWrapper<Permission> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Permission::getStatus, 1).orderByAsc(Permission::getSortOrder);
        List<Permission> allPermissions = permissionMapper.selectList(wrapper);

        List<PermissionTreeVO> voList = allPermissions.stream().map(p -> {
            PermissionTreeVO vo = new PermissionTreeVO();
            BeanUtils.copyProperties(p, vo);
            return vo;
        }).collect(Collectors.toList());

        Map<Long, List<PermissionTreeVO>> grouped = voList.stream()
                .collect(Collectors.groupingBy(PermissionTreeVO::getParentId));

        List<PermissionTreeVO> roots = grouped.getOrDefault(0L, new ArrayList<>());
        buildTree(roots, grouped);
        return roots;
    }

    private void buildTree(List<PermissionTreeVO> parents, Map<Long, List<PermissionTreeVO>> grouped) {
        for (PermissionTreeVO parent : parents) {
            List<PermissionTreeVO> children = grouped.getOrDefault(parent.getId(), new ArrayList<>());
            parent.setChildren(children);
            if (!children.isEmpty()) {
                buildTree(children, grouped);
            }
        }
    }

    @Override
    public void createPermission(PermissionDTO dto) {
        LambdaQueryWrapper<Permission> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Permission::getPermissionCode, dto.getPermissionCode());
        if (permissionMapper.selectCount(wrapper) > 0) {
            throw new BusinessException(ResultCode.DATA_CONFLICT, "权限编码已存在");
        }

        Permission permission = new Permission();
        BeanUtils.copyProperties(dto, permission);
        permission.setParentId(dto.getParentId() != null ? dto.getParentId() : 0L);
        permission.setStatus(dto.getStatus() != null ? dto.getStatus() : 1);
        permissionMapper.insert(permission);
    }

    @Override
    public void updatePermission(PermissionDTO dto) {
        Permission existing = permissionMapper.selectById(dto.getId());
        if (existing == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "权限不存在");
        }

        Permission permission = new Permission();
        BeanUtils.copyProperties(dto, permission);
        permissionMapper.updateById(permission);
    }

    @Override
    public void deletePermission(Long id) {
        permissionMapper.deleteById(id);
    }
}
