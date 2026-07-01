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
public class CustomerReportVO {

    private long totalCustomers;
    private long newCustomers;
    private long activeCustomers;
    private BigDecimal totalDeposits;
    private BigDecimal totalWithdrawals;
    private long totalTrades;
    private BigDecimal avgDeposit;
    private BigDecimal avgTradesPerCustomer;
}
