package com.globalfx.crm.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.globalfx.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("crm_customer")
public class Customer extends BaseEntity {

    private String customerNo;
    private Long tenantId;
    private Integer customerType;
    private String firstName;
    private String lastName;
    private String fullName;
    private String email;
    private String phone;
    private String country;
    private String city;
    private String address;
    private LocalDate birthDate;
    private Integer idCardType;
    private String idCardNo;
    private Integer riskScore;
    private Integer riskLevel;
    private String segment;
    private Integer source;
    private Long ibId;
    private Long managerId;
    private Integer status;
    private LocalDateTime lastActiveTime;
    private BigDecimal totalDeposit;
    private BigDecimal totalWithdrawal;
    private Integer totalTrades;
}
