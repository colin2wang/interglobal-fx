package com.globalfx.report.controller;

import com.globalfx.common.result.Result;
import com.globalfx.report.service.DashboardService;
import com.globalfx.report.vo.DashboardVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "仪表盘")
@RestController
@RequestMapping("/api/v1/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @Operation(summary = "获取仪表盘概览")
    @GetMapping("/summary")
    public Result<DashboardVO> getSummary() {
        DashboardVO summary = dashboardService.getSummary();
        return Result.success(summary);
    }
}
