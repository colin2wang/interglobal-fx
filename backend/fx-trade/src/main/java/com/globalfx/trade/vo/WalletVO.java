package com.globalfx.trade.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class WalletVO {

    private Long id;
    private UUID walletNo;
    private Long accountId;
    private String currency;
    private BigDecimal balance;
    private BigDecimal frozenBalance;
    private BigDecimal totalIn;
    private BigDecimal totalOut;
    private Integer status;
}
