package com.globalfx.crm.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class IbPartnerDTO {

    private Long id;

    @NotBlank(message = "合作伙伴姓名不能为空")
    private String fullName;

    private Integer partnerLevel;
    private Long parentId;
    private Integer commissionType;
    private BigDecimal rebateRatio;
    private Integer status;
    private String brandName;
    private String domain;
    private String remark;
}
