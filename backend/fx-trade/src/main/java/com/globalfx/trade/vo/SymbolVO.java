package com.globalfx.trade.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SymbolVO {

    private Long id;
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
    private String tradingHours;
    private Integer status;
}
