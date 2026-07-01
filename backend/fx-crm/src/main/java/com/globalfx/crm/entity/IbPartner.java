package com.globalfx.crm.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.globalfx.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ib_partner")
public class IbPartner extends BaseEntity {

    private String partnerNo;
    private Long tenantId;
    private Long customerId;
    private Integer partnerLevel;
    private Long parentId;
    private String hierarchyPath;
    private Integer commissionType;
    private String commissionConfig;
    private BigDecimal rebateRatio;
    private Integer status;
    private Integer totalCustomers;
    private Integer activeCustomers;
    private BigDecimal totalCommission;
    private BigDecimal pendingCommission;
    private BigDecimal paidCommission;
    private String brandName;
    private String domain;
    private String logoUrl;
}
