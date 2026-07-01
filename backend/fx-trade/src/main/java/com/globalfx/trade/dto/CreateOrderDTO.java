package com.globalfx.trade.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateOrderDTO {

    @NotNull(message = "账户ID不能为空")
    private Long accountId;

    @NotNull(message = "品种ID不能为空")
    private Long symbolId;

    @NotNull(message = "订单类型不能为空")
    private Integer orderType;

    @NotNull(message = "订单方向不能为空")
    private Integer orderSide;

    private BigDecimal orderPrice;
    private BigDecimal stopPrice;

    @NotNull(message = "手数不能为空")
    @Positive(message = "手数必须大于0")
    private BigDecimal lotSize;

    private Integer timeInForce;
    private String clientOrderId;
    private String orderText;
}
