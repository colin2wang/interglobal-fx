package com.globalfx.risk.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.globalfx.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("rsk_rule")
public class RiskRule extends BaseEntity {

    private String ruleCode;
    private String ruleName;
    private Integer ruleType;
    private Long symbolId;
    private String accountGroup;
    private Integer riskLevel;
    private BigDecimal minValue;
    private BigDecimal maxValue;
    private Integer action;
    private Integer actionLevel;
    private Integer priority;
    private Integer status;
    private LocalDateTime effectiveStart;
    private LocalDateTime effectiveEnd;
    private String ruleExpression;
    private Long tenantId;
}
