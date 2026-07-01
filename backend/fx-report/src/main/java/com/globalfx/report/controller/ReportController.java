package com.globalfx.report.controller;

import com.globalfx.common.result.Result;
import com.globalfx.report.dto.CustomerReportDTO;
import com.globalfx.report.dto.TradeReportDTO;
import com.globalfx.report.service.CustomerReportService;
import com.globalfx.report.service.TradeReportService;
import com.globalfx.report.vo.CustomerReportVO;
import com.globalfx.report.vo.TradeReportVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "报表管理")
@RestController
@RequestMapping("/api/v1/report")
@RequiredArgsConstructor
public class ReportController {

    private final TradeReportService tradeReportService;
    private final CustomerReportService customerReportService;

    @Operation(summary = "获取交易报表")
    @GetMapping("/trade-report")
    public Result<TradeReportVO> getTradeReport(TradeReportDTO dto) {
        TradeReportVO report = tradeReportService.getTradeReport(dto);
        return Result.success(report);
    }

    @Operation(summary = "获取客户报表")
    @GetMapping("/customer-report")
    public Result<CustomerReportVO> getCustomerReport(CustomerReportDTO dto) {
        CustomerReportVO report = customerReportService.getCustomerReport(dto);
        return Result.success(report);
    }
}
