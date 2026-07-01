package com.globalfx.clearing.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("clr_daily_settlement")
public class DailySettlement {

    @TableId(type = IdType.AUTO)
    private Long id;
    private String settlementNo;
    private Long tenantId;
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
    private BigDecimal marginBefore;
    private BigDecimal marginAfter;
    private Integer status;
    private String errorMessage;
    private LocalDateTime processedTime;
    private LocalDateTime createdTime;
    private Integer isDeleted;
}
