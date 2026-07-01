package com.globalfx.report.service.impl;

import com.globalfx.report.mapper.DashboardMapper;
import com.globalfx.report.service.DashboardService;
import com.globalfx.report.vo.DashboardVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final DashboardMapper dashboardMapper;

    @Override
    public DashboardVO getSummary() {
        long totalCustomers = dashboardMapper.countTotalCustomers();
        long activeAccounts = dashboardMapper.countActiveAccounts();
        long totalOrders = dashboardMapper.countTotalOrders();
        BigDecimal totalEquity = dashboardMapper.sumTotalBalance();
        long openPositions = dashboardMapper.countOpenPositions();
        BigDecimal unrealizedPnl = dashboardMapper.sumUnrealizedPnl();

        return DashboardVO.builder()
                .totalCustomers(totalCustomers)
                .activeAccounts(activeAccounts)
                .totalOrders(totalOrders)
                .totalVolume(BigDecimal.ZERO)
                .totalDeposit(BigDecimal.ZERO)
                .totalWithdrawal(BigDecimal.ZERO)
                .totalEquity(totalEquity)
                .openPositions(openPositions)
                .unrealizedPnl(unrealizedPnl)
                .build();
    }
}
