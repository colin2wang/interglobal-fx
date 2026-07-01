package com.globalfx.crm.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class CustomerVO {

    private Long id;
    private String customerNo;
    private Integer customerType;
    private String fullName;
    private String email;
    private String phone;
    private String country;
    private String city;
    private Integer riskLevel;
    private Integer status;
    private BigDecimal totalDeposit;
    private BigDecimal totalWithdrawal;
    private Integer totalTrades;
    private LocalDateTime lastActiveTime;
    private LocalDateTime createdTime;
}
