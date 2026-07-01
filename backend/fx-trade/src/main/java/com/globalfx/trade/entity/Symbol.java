package com.globalfx.trade.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.globalfx.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sym_symbol")
public class Symbol extends BaseEntity {

    private String symbol;
    private String symbolName;
    private Integer symbolType;
    private String baseCurrency;
    private String quoteCurrency;
    private Integer precision;
    private BigDecimal lotSize;
    private BigDecimal minLot;
    private BigDecimal maxLot;
    private BigDecimal lotStep;
    private Integer minDistancePoints;
    private Integer tradeMode;
    private BigDecimal contractSize;
    private BigDecimal swapLong;
    private BigDecimal swapShort;
    private Integer swapMode;
    private String tradingHours;
    private Integer status;
    private Long tenantId;
}
