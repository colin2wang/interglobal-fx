package com.globalfx.report.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CustomerReportDTO {

    private Long ibId;
    private Long managerId;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer status;
}
