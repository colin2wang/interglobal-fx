package com.globalfx.trade.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@TableName("wlt_transaction")
public class WalletTransaction {

    @TableId(type = IdType.AUTO)
    private Long id;
    private UUID txnNo;
    private Long tenantId;
    private Long walletId;
    private Long accountId;
    private String currency;
    private Integer txnType;
    private Integer direction;
    private BigDecimal amount;
    private BigDecimal balanceBefore;
    private BigDecimal balanceAfter;
    private BigDecimal frozenBefore;
    private BigDecimal frozenAfter;
    private UUID relatedOrderNo;
    private UUID relatedTxnNo;
    private Integer status;
    private String referenceId;
    private String remark;
    private LocalDateTime createdTime;
}
