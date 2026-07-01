package com.globalfx.clearing.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class SettlementVO {

    private Long id;
    private String settlementNo;
    private Long accountId;
    private LocalDate settlementDate;
    private BigDecimal openEquity;
    private BigDecimal closeEquity;
    private BigDecimal realizedPnl;
    private BigDecimal unrealizedPnl;
    private BigDecimal swapAmount;
    private BigDecimal commission;
    private BigDecimal deposit;
    private BigDecimal withdrawal;
    private BigDecimal ibCommission;
    private BigDecimal balanceBefore;
    private BigDecimal balanceAfter;
    private Integer status;
    private String errorMessage;
    private LocalDateTime processedTime;
    private LocalDateTime createdTime;
}
