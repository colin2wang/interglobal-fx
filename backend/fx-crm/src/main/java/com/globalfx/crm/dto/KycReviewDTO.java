package com.globalfx.crm.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class KycReviewDTO {

    @NotNull(message = "申请ID不能为空")
    private Long applicationId;

    @NotNull(message = "审核结果不能为空")
    private Integer status;

    private String rejectReason;
    private Long operatorId;
    private String operateRemark;
}
