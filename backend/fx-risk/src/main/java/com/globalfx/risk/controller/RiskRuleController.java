package com.globalfx.risk.controller;

import com.globalfx.common.result.PageResult;
import com.globalfx.common.result.Result;
import com.globalfx.risk.dto.RiskRuleDTO;
import com.globalfx.risk.service.RiskRuleService;
import com.globalfx.risk.vo.RiskRuleVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "风控规则管理")
@RestController
@RequestMapping("/api/v1/risk/rules")
@RequiredArgsConstructor
public class RiskRuleController {

    private final RiskRuleService riskRuleService;

    @Operation(summary = "分页查询风控规则")
    @GetMapping
    public Result<PageResult<RiskRuleVO>> list(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "20") int pageSize,
            @RequestParam(required = false) Integer status) {
        PageResult<RiskRuleVO> result = riskRuleService.pageList(pageNum, pageSize, status);
        return Result.success(result);
    }

    @Operation(summary = "获取风控规则详情")
    @GetMapping("/{id}")
    public Result<RiskRuleVO> getDetail(@PathVariable Long id) {
        RiskRuleVO rule = riskRuleService.getById(id);
        return Result.success(rule);
    }

    @Operation(summary = "创建风控规则")
    @PostMapping
    public Result<Void> create(@Valid @RequestBody RiskRuleDTO dto) {
        riskRuleService.createRule(dto);
        return Result.success();
    }

    @Operation(summary = "更新风控规则")
    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable Long id, @Valid @RequestBody RiskRuleDTO dto) {
        dto.setId(id);
        riskRuleService.updateRule(dto);
        return Result.success();
    }

    @Operation(summary = "删除风控规则")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        riskRuleService.deleteRule(id);
        return Result.success();
    }
}
