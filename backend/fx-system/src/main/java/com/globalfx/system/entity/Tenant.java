package com.globalfx.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.globalfx.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("cfg_tenant")
public class Tenant extends BaseEntity {

    private String tenantCode;
    private String tenantName;
    private Integer tenantType;
    private String logoUrl;
    private String domain;
    private String regionCode;
    private String licenseNo;
    private String contactEmail;
    private String contactPhone;
    private String config;
    private BigDecimal defaultLeverage;
    private String defaultCurrency;
    private Integer status;
    private LocalDateTime expireTime;
}
