package com.globalfx.system.controller;

import com.globalfx.common.result.Result;
import com.globalfx.system.dto.PermissionDTO;
import com.globalfx.system.service.PermissionService;
import com.globalfx.system.vo.PermissionTreeVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "权限管理")
@RestController
@RequestMapping("/api/v1/system/permissions")
@RequiredArgsConstructor
public class PermissionController {

    private final PermissionService permissionService;

    @Operation(summary = "获取权限树")
    @GetMapping
    public Result<List<PermissionTreeVO>> tree() {
        List<PermissionTreeVO> tree = permissionService.getPermissionTree();
        return Result.success(tree);
    }

    @Operation(summary = "创建权限")
    @PostMapping
    public Result<Void> create(@Valid @RequestBody PermissionDTO dto) {
        permissionService.createPermission(dto);
        return Result.success();
    }

    @Operation(summary = "更新权限")
    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable Long id, @Valid @RequestBody PermissionDTO dto) {
        dto.setId(id);
        permissionService.updatePermission(dto);
        return Result.success();
    }

    @Operation(summary = "删除权限")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        permissionService.deletePermission(id);
        return Result.success();
    }
}
