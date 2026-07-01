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
public class TradeReportVO {

    private long totalOrders;
    private long totalVolume;
    private BigDecimal totalTurnover;
    private BigDecimal totalCommission;
    private BigDecimal totalSwap;
    private BigDecimal totalRealizedPnl;
    private long winningTrades;
    private long losingTrades;
    private BigDecimal winRate;
    private BigDecimal avgProfit;
    private BigDecimal avgLoss;
    private BigDecimal profitFactor;
}
