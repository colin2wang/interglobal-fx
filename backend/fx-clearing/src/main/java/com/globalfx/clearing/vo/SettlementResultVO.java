package com.globalfx.clearing.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SettlementResultVO {

    private int totalAccounts;
    private int successCount;
    private int failCount;
    private String message;
}
