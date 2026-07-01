package com.globalfx.trade.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuoteVO {

    private String symbol;
    private BigDecimal bid;
    private BigDecimal ask;
    private BigDecimal spread;
    private Long timestamp;
}
