package com.globalfx.clearing.controller;

import com.globalfx.common.result.Result;
import com.globalfx.clearing.dto.ExecuteSettlementDTO;
import com.globalfx.clearing.dto.SettlementQueryDTO;
import com.globalfx.clearing.service.SettlementService;
import com.globalfx.clearing.vo.SettlementResultVO;
import com.globalfx.clearing.vo.SettlementVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "日结清管理")
@RestController
@RequestMapping("/api/v1/clearing/settlements")
@RequiredArgsConstructor
public class SettlementController {

    private final SettlementService settlementService;

    @Operation(summary = "查询日结清列表")
    @GetMapping
    public Result<List<SettlementVO>> list(SettlementQueryDTO query) {
        List<SettlementVO> list = settlementService.pageList(query);
        return Result.success(list);
    }

    @Operation(summary = "执行日结算")
    @PostMapping("/execute")
    public Result<SettlementResultVO> execute(@Valid @RequestBody ExecuteSettlementDTO dto) {
        SettlementResultVO result = settlementService.executeDailySettlement(dto);
        return Result.success(result);
    }
}
