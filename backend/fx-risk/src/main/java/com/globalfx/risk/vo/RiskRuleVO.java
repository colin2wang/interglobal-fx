package com.globalfx.risk.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class RiskRuleVO {

    private Long id;
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
    private LocalDateTime createdTime;
    private String remark;
}
