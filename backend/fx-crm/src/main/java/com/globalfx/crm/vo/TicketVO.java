package com.globalfx.crm.vo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class TicketVO {

    private Long id;
    private String ticketNo;
    private Long customerId;
    private String customerName;
    private Integer category;
    private Integer priority;
    private String title;
    private String content;
    private UUID relatedOrderNo;
    private Integer status;
    private Long assignedTo;
    private String assignedName;
    private LocalDateTime firstReplyTime;
    private LocalDateTime resolvedTime;
    private Integer satisfaction;
    private LocalDateTime createdTime;
}
