package com.globalfx.report.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardVO {

    private long totalCustomers;
    private long activeAccounts;
    private long totalOrders;
    private BigDecimal totalVolume;
    private BigDecimal totalDeposit;
    private BigDecimal totalWithdrawal;
    private BigDecimal totalEquity;
    private long openPositions;
    private BigDecimal unrealizedPnl;
}
