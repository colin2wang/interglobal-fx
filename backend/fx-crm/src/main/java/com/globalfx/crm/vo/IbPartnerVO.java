package com.globalfx.crm.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class IbPartnerVO {

    private Long id;
    private String partnerNo;
    private Long customerId;
    private String customerName;
    private Integer partnerLevel;
    private Integer commissionType;
    private BigDecimal rebateRatio;
    private Integer status;
    private Integer totalCustomers;
    private Integer activeCustomers;
    private BigDecimal totalCommission;
    private String brandName;
    private LocalDateTime createdTime;
}
