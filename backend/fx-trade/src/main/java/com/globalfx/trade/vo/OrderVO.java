package com.globalfx.trade.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class OrderVO {

    private Long id;
    private UUID orderNo;
    private Long accountId;
    private Long symbolId;
    private String symbol;
    private Integer orderType;
    private Integer orderSide;
    private BigDecimal orderPrice;
    private BigDecimal stopPrice;
    private BigDecimal lotSize;
    private BigDecimal filledLot;
    private BigDecimal avgPrice;
    private Integer status;
    private Integer executionType;
    private Integer timeInForce;
    private LocalDateTime expireTime;
    private String orderText;
    private Integer riskCheckStatus;
    private LocalDateTime createdTime;
    private String remark;
}
