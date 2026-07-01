package com.globalfx.crm.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@TableName("kyc_application")
public class KycApplication {

    @TableId(type = IdType.AUTO)
    private Long id;
    private UUID applicationNo;
    private Long tenantId;
    private Long customerId;
    private Integer kycLevel;
    private String kycProvider;
    private String providerRefId;
    private Integer status;
    private String rejectReason;
    private String verifyResult;
    private Integer riskScore;
    private Integer amlCheckResult;
    private Integer sanctionsHit;
    private Long operatorId;
    private LocalDateTime operateTime;
    private String operateRemark;
    private LocalDateTime expireTime;
    private Long createdBy;
    private LocalDateTime createdTime;
    private Long updatedBy;
    private LocalDateTime updatedTime;
    private Integer isDeleted;
    private String remark;
}
