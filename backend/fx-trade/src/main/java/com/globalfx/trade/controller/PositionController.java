package com.globalfx.trade.controller;

import com.globalfx.common.result.PageResult;
import com.globalfx.common.result.Result;
import com.globalfx.trade.dto.ClosePositionDTO;
import com.globalfx.trade.service.PositionService;
import com.globalfx.trade.vo.PositionVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "持仓管理")
@RestController
@RequestMapping("/api/v1/trade/positions")
@RequiredArgsConstructor
public class PositionController {

    private final PositionService positionService;

    @Operation(summary = "分页查询持仓")
    @GetMapping
    public Result<PageResult<PositionVO>> list(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "20") int pageSize,
            @RequestParam(required = false) Long accountId) {
        PageResult<PositionVO> result = positionService.pageList(pageNum, pageSize, accountId);
        return Result.success(result);
    }

    @Operation(summary = "平仓")
    @PostMapping("/close")
    public Result<Void> close(@Valid @RequestBody ClosePositionDTO dto) {
        positionService.closePosition(dto);
        return Result.success();
    }
}
