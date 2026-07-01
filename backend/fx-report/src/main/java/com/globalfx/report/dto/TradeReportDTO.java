package com.globalfx.report.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TradeReportDTO {

    private Long accountId;
    private Long symbolId;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer orderType;
    private Integer orderSide;
}
