package com.globalfx.clearing.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SettlementQueryDTO {

    private Long accountId;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer status;
}
