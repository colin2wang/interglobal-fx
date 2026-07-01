package com.globalfx.trade.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class AccountVO {

    private Long id;
    private String accountNo;
    private Long customerId;
    private Integer accountType;
    private String accountGroup;
    private String baseCurrency;
    private BigDecimal leverage;
    private BigDecimal balance;
    private BigDecimal equity;
    private BigDecimal margin;
    private BigDecimal freeMargin;
    private BigDecimal marginLevel;
    private BigDecimal credit;
    private BigDecimal unrealizedPnl;
    private BigDecimal realizedPnl;
    private Integer status;
    private Integer riskLevel;
    private Integer kycStatus;
    private LocalDateTime lastTradeTime;
    private LocalDateTime createdTime;
}
