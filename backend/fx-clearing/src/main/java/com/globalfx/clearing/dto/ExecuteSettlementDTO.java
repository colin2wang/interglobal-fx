package com.globalfx.clearing.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ExecuteSettlementDTO {

    @NotNull(message = "结算日期不能为空")
    private LocalDate settlementDate;

    private Long tenantId;
}
