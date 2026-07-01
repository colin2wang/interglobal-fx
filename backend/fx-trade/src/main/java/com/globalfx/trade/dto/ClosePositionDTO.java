package com.globalfx.trade.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ClosePositionDTO {

    @NotNull(message = "持仓ID不能为空")
    private Long positionId;

    private BigDecimal closeLot;
    private BigDecimal closePrice;
}
