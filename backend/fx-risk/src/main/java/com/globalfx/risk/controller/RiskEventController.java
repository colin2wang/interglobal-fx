package com.globalfx.risk.controller;

import com.globalfx.common.result.PageResult;
import com.globalfx.common.result.Result;
import com.globalfx.risk.dto.HandleEventDTO;
import com.globalfx.risk.dto.OrderCheckDTO;
import com.globalfx.risk.service.RiskEngineService;
import com.globalfx.risk.service.RiskEventService;
import com.globalfx.risk.vo.RiskCheckResult;
import com.globalfx.risk.vo.RiskEventVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "风控事件管理")
@RestController
@RequestMapping("/api/v1/risk/events")
@RequiredArgsConstructor
public class RiskEventController {

    private final RiskEventService riskEventService;
    private final RiskEngineService riskEngineService;

    @Operation(summary = "分页查询风控事件")
    @GetMapping
    public Result<PageResult<RiskEventVO>> list(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "20") int pageSize,
            @RequestParam(required = false) Integer status) {
        PageResult<RiskEventVO> result = riskEventService.pageList(pageNum, pageSize, status);
        return Result.success(result);
    }

    @Operation(summary = "处理风控事件")
    @PostMapping("/handle")
    public Result<Void> handle(@Valid @RequestBody HandleEventDTO dto) {
        riskEventService.handleEvent(dto);
        return Result.success();
    }

    @Operation(summary = "订单风控检查")
    @PostMapping("/check")
    public Result<RiskCheckResult> check(@Valid @RequestBody OrderCheckDTO dto) {
        RiskCheckResult result = riskEngineService.checkOrder(dto);
        return Result.success(result);
    }
}
