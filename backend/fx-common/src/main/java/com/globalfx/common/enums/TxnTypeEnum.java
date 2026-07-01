package com.globalfx.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TxnTypeEnum {
    DEPOSIT(1, "入金"), WITHDRAWAL(2, "出金"), TRANSFER(3, "转账"), TRADE(4, "交易"),
    SETTLEMENT(5, "清算"), COMMISSION(6, "佣金"), INTEREST(7, "利息"), FEE(8, "手续费"), OTHER(9, "其他");
    private final int code;
    private final String description;
    public static TxnTypeEnum of(int code) {
        for (TxnTypeEnum e : values()) { if (e.code == code) return e; }
        throw new IllegalArgumentException("Unknown txn type: " + code);
    }
}
