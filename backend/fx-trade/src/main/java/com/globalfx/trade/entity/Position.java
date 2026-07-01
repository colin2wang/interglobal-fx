package com.globalfx.trade.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.globalfx.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("pos_position")
public class Position extends BaseEntity {

    private UUID positionNo;
    private Long tenantId;
    private Long accountId;
    private Long symbolId;
    private Integer positionSide;
    private BigDecimal openLot;
    private BigDecimal currentLot;
    private BigDecimal openPrice;
    private Long openOrderId;
    private BigDecimal closeLot;
    private BigDecimal closePrice;
    private BigDecimal realizedPnl;
    private BigDecimal unrealizedPnl;
    private BigDecimal swapAmount;
    private BigDecimal commission;
    private BigDecimal usedMargin;
    private LocalDateTime openTime;
    private LocalDateTime updateTime;
    private Integer isLocked;
    private String lockReason;
    private Integer status;
}
