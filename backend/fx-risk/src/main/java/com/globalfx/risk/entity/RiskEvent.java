package com.globalfx.risk.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@TableName("rsk_event")
public class RiskEvent {

    @TableId(type = IdType.AUTO)
    private Long id;
    private UUID eventNo;
    private Long tenantId;
    private Long ruleId;
    private String ruleCode;
    private Integer eventType;
    private Long accountId;
    private Long symbolId;
    private UUID orderNo;
    private UUID positionNo;
    private String eventData;
    private Integer actionTaken;
    private Long operatorId;
    private LocalDateTime operateTime;
    private String operateResult;
    private Integer status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
    private Integer isDeleted;
    private String remark;
}
