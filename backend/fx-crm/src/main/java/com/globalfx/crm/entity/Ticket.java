package com.globalfx.crm.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@TableName("tic_ticket")
public class Ticket {

    @TableId(type = IdType.AUTO)
    private Long id;
    private String ticketNo;
    private Long tenantId;
    private Long customerId;
    private Long accountId;
    private Integer category;
    private Integer priority;
    private String title;
    private String content;
    private String attachments;
    private UUID relatedOrderNo;
    private Integer status;
    private Long assignedTo;
    private LocalDateTime assignedTime;
    private LocalDateTime firstReplyTime;
    private LocalDateTime resolvedTime;
    private LocalDateTime closedTime;
    private Integer satisfaction;
    private LocalDateTime slaDeadline;
    private Integer slaBreached;
    private Long createdBy;
    private LocalDateTime createdTime;
    private Long updatedBy;
    private LocalDateTime updatedTime;
    private Integer isDeleted;
    private String remark;
}
