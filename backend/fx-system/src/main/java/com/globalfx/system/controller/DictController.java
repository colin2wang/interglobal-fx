package com.globalfx.system.controller;

import com.globalfx.common.result.Result;
import com.globalfx.system.dto.DictDataDTO;
import com.globalfx.system.dto.DictTypeDTO;
import com.globalfx.system.entity.DictData;
import com.globalfx.system.entity.DictType;
import com.globalfx.system.service.DictService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "字典管理")
@RestController
@RequiredArgsConstructor
public class DictController {

    private final DictService dictService;

    @Operation(summary = "查询字典类型列表")
    @GetMapping("/api/v1/system/dict-types")
    public Result<List<DictType>> listDictTypes(@RequestParam(required = false) Integer status) {
        List<DictType> list = dictService.listDictTypes(status);
        return Result.success(list);
    }

    @Operation(summary = "创建字典类型")
    @PostMapping("/api/v1/system/dict-types")
    public Result<Void> createDictType(@Valid @RequestBody DictTypeDTO dto) {
        dictService.createDictType(dto);
        return Result.success();
    }

    @Operation(summary = "更新字典类型")
    @PutMapping("/api/v1/system/dict-types/{id}")
    public Result<Void> updateDictType(@PathVariable Long id, @Valid @RequestBody DictTypeDTO dto) {
        dto.setId(id);
        dictService.updateDictType(dto);
        return Result.success();
    }

    @Operation(summary = "删除字典类型")
    @DeleteMapping("/api/v1/system/dict-types/{id}")
    public Result<Void> deleteDictType(@PathVariable Long id) {
        dictService.deleteDictType(id);
        return Result.success();
    }

    @Operation(summary = "根据字典类型查询字典数据")
    @GetMapping("/api/v1/system/dict-data")
    public Result<List<DictData>> listDictData(@RequestParam String dictType) {
        List<DictData> list = dictService.listDictDataByType(dictType);
        return Result.success(list);
    }

    @Operation(summary = "创建字典数据")
    @PostMapping("/api/v1/system/dict-data")
    public Result<Void> createDictData(@Valid @RequestBody DictDataDTO dto) {
        dictService.createDictData(dto);
        return Result.success();
    }

    @Operation(summary = "更新字典数据")
    @PutMapping("/api/v1/system/dict-data/{id}")
    public Result<Void> updateDictData(@PathVariable Long id, @Valid @RequestBody DictDataDTO dto) {
        dto.setId(id);
        dictService.updateDictData(dto);
        return Result.success();
    }

    @Operation(summary = "删除字典数据")
    @DeleteMapping("/api/v1/system/dict-data/{id}")
    public Result<Void> deleteDictData(@PathVariable Long id) {
        dictService.deleteDictData(id);
        return Result.success();
    }
}
