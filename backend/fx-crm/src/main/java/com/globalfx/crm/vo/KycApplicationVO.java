package com.globalfx.crm.vo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class KycApplicationVO {

    private Long id;
    private UUID applicationNo;
    private Long customerId;
    private String customerName;
    private Integer kycLevel;
    private Integer status;
    private String rejectReason;
    private Integer riskScore;
    private Integer sanctionsHit;
    private LocalDateTime createdTime;
}
