package com.globalfx.risk.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RiskCheckResult {

    private boolean passed;
    private Integer action;
    private String message;
    private String ruleCode;
    private Long ruleId;
}
