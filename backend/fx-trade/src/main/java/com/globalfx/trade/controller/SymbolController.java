package com.globalfx.trade.controller;

import com.globalfx.common.result.Result;
import com.globalfx.trade.service.SymbolService;
import com.globalfx.trade.vo.SymbolVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "品种管理")
@RestController
@RequestMapping("/api/v1/trade/symbols")
@RequiredArgsConstructor
public class SymbolController {

    private final SymbolService symbolService;

    @Operation(summary = "查询所有品种")
    @GetMapping
    public Result<List<SymbolVO>> list(@RequestParam(required = false) Integer status) {
        List<SymbolVO> list = symbolService.listAll(status);
        return Result.success(list);
    }

    @Operation(summary = "获取品种详情")
    @GetMapping("/{id}")
    public Result<SymbolVO> getDetail(@PathVariable Long id) {
        SymbolVO symbol = symbolService.getDetail(id);
        return Result.success(symbol);
    }
}
