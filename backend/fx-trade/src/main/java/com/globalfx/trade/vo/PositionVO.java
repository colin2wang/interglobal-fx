package com.globalfx.trade.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class PositionVO {

    private Long id;
    private UUID positionNo;
    private Long accountId;
    private Long symbolId;
    private String symbol;
    private Integer positionSide;
    private BigDecimal openLot;
    private BigDecimal currentLot;
    private BigDecimal openPrice;
    private BigDecimal closePrice;
    private BigDecimal realizedPnl;
    private BigDecimal unrealizedPnl;
    private BigDecimal swapAmount;
    private BigDecimal commission;
    private BigDecimal usedMargin;
    private LocalDateTime openTime;
    private Integer status;
    private Integer isLocked;
}
