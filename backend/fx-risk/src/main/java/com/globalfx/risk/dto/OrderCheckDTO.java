package com.globalfx.risk.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderCheckDTO {

    private Long accountId;
    private Long symbolId;
    private Integer orderType;
    private Integer orderSide;
    private BigDecimal orderPrice;
    private BigDecimal lotSize;
    private BigDecimal currentBalance;
    private BigDecimal currentMargin;
    private BigDecimal freeMargin;
}
