package com.globalfx.risk.vo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class RiskEventVO {

    private Long id;
    private UUID eventNo;
    private Long ruleId;
    private String ruleCode;
    private Integer eventType;
    private Long accountId;
    private Long symbolId;
    private UUID orderNo;
    private UUID positionNo;
    private Integer actionTaken;
    private Long operatorId;
    private LocalDateTime operateTime;
    private String operateResult;
    private Integer status;
    private LocalDateTime createdTime;
    private String remark;
}
