package com.globalfx.system.controller;

import com.globalfx.common.result.Result;
import com.globalfx.system.dto.RoleDTO;
import com.globalfx.system.service.RoleService;
import com.globalfx.system.vo.RoleVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "角色管理")
@RestController
@RequestMapping("/api/v1/system/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @Operation(summary = "查询所有角色")
    @GetMapping
    public Result<List<RoleVO>> list(@RequestParam(required = false) Integer status) {
        List<RoleVO> list = roleService.listAll(status);
        return Result.success(list);
    }

    @Operation(summary = "获取角色详情")
    @GetMapping("/{id}")
    public Result<RoleVO> getDetail(@PathVariable Long id) {
        RoleVO vo = roleService.getById(id);
        return Result.success(vo);
    }

    @Operation(summary = "获取角色权限ID列表")
    @GetMapping("/{id}/permissions")
    public Result<List<Long>> getPermissionIds(@PathVariable Long id) {
        List<Long> permissionIds = roleService.getPermissionIds(id);
        return Result.success(permissionIds);
    }

    @Operation(summary = "创建角色")
    @PostMapping
    public Result<Void> create(@Valid @RequestBody RoleDTO dto) {
        roleService.createRole(dto);
        return Result.success();
    }

    @Operation(summary = "更新角色")
    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable Long id, @Valid @RequestBody RoleDTO dto) {
        dto.setId(id);
        roleService.updateRole(dto);
        return Result.success();
    }

    @Operation(summary = "删除角色")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        roleService.deleteRole(id);
        return Result.success();
    }
}
