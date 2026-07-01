package com.globalfx.risk.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class RiskRuleDTO {

    private Long id;

    @NotBlank(message = "规则编码不能为空")
    private String ruleCode;

    @NotBlank(message = "规则名称不能为空")
    private String ruleName;

    @NotNull(message = "规则类型不能为空")
    private Integer ruleType;

    private Long symbolId;
    private String accountGroup;
    private Integer riskLevel;
    private BigDecimal minValue;
    private BigDecimal maxValue;

    @NotNull(message = "动作不能为空")
    private Integer action;

    private Integer actionLevel;
    private Integer priority;
    private Integer status;
    private LocalDateTime effectiveStart;
    private LocalDateTime effectiveEnd;
    private String ruleExpression;
    private String remark;
}
