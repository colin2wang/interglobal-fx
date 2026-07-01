package com.globalfx.crm.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("tic_reply")
public class TicketReply {

    @TableId(type = IdType.AUTO)
    private Long id;
    private Long ticketId;
    private Long tenantId;
    private String content;
    private String attachments;
    private Integer replyType;
    private Long createdBy;
    private LocalDateTime createdTime;
    private Integer isDeleted;
}
