package com.globalfx.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RiskEventTypeEnum {
    ORDER_REJECTED(1, "订单拒绝"), WARNING(2, "风控警告"), FORCE_LIQUIDATION(3, "强制平仓"),
    ACCOUNT_FROZEN(4, "账户冻结"), ANOMALY_DETECTED(5, "异常检测");
    private final int code;
    private final String description;
    public static RiskEventTypeEnum of(int code) {
        for (RiskEventTypeEnum e : values()) { if (e.code == code) return e; }
        throw new IllegalArgumentException("Unknown risk event type: " + code);
    }
}
