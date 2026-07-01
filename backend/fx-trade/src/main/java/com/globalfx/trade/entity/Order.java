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
@TableName("trd_order")
public class Order extends BaseEntity {

    private UUID orderNo;
    private Long tenantId;
    private Long accountId;
    private Long symbolId;
    private Integer orderType;
    private Integer orderSide;
    private BigDecimal orderPrice;
    private BigDecimal stopPrice;
    private BigDecimal lotSize;
    private BigDecimal filledLot;
    private BigDecimal avgPrice;
    private BigDecimal orderPriceShow;
    private Integer slippagePoints;
    private Integer slippageType;
    private Integer status;
    private Integer executionType;
    private Integer timeInForce;
    private LocalDateTime expireTime;
    private Long positionId;
    private Long parentOrderId;
    private Long lpId;
    private Integer executionMode;
    private String clientOrderId;
    private Integer channel;
    private String ipAddress;
    private String orderText;
    private String reason;
    private Integer riskCheckStatus;
    private LocalDateTime riskCheckTime;
    private String riskRuleIds;
    private UUID sourceOrderNo;
}
