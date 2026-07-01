package com.globalfx.trade.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.globalfx.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("acc_account")
public class Account extends BaseEntity {

    private String accountNo;
    private Long tenantId;
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
    private BigDecimal depositAmount;
    private BigDecimal withdrawAmount;
    private Integer status;
    private String freezeReason;
    private Long ibId;
    private Long managerId;
    private Integer platform;
    private String platformAccountId;
    private String regionCode;
    private Integer riskLevel;
    private Integer kycStatus;
    private LocalDateTime lastTradeTime;
    private LocalDateTime lastLoginTime;
    private Integer version;
}
