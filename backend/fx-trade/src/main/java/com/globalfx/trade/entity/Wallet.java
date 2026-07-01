package com.globalfx.trade.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@TableName("wlt_wallet")
public class Wallet {

    @TableId(type = IdType.AUTO)
    private Long id;
    private UUID walletNo;
    private Long tenantId;
    private Long accountId;
    private String currency;
    private BigDecimal balance;
    private BigDecimal frozenBalance;
    private BigDecimal totalIn;
    private BigDecimal totalOut;
    private Integer status;
    private Integer version;
    private Long createdBy;
    private LocalDateTime createdTime;
    private Long updatedBy;
    private LocalDateTime updatedTime;
    private Integer isDeleted;
    private String remark;
}
