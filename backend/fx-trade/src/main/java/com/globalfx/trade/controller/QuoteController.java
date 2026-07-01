package com.globalfx.trade.controller;

import com.globalfx.common.result.Result;
import com.globalfx.trade.service.QuoteService;
import com.globalfx.trade.vo.KlineVO;
import com.globalfx.trade.vo.QuoteVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "行情报价")
@RestController
@RequestMapping("/api/v1/trade/quotes")
@RequiredArgsConstructor
public class QuoteController {

    private final QuoteService quoteService;

    @Operation(summary = "获取多个品种报价")
    @GetMapping("/prices")
    public Result<List<QuoteVO>> getPrices(@RequestParam List<String> symbols) {
        List<QuoteVO> prices = quoteService.getPrices(symbols);
        return Result.success(prices);
    }

    @Operation(summary = "获取K线数据")
    @GetMapping("/klines")
    public Result<List<KlineVO>> getKlines(
            @RequestParam String symbol,
            @RequestParam(defaultValue = "1m") String interval,
            @RequestParam(defaultValue = "100") int limit) {
        List<KlineVO> klines = quoteService.getKlines(symbol, interval, limit);
        return Result.success(klines);
    }
}
