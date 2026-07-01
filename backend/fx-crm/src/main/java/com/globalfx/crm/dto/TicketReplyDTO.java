package com.globalfx.crm.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TicketReplyDTO {

    @NotNull(message = "工单ID不能为空")
    private Long ticketId;

    @NotBlank(message = "回复内容不能为空")
    private String content;

    private Integer replyType;
    private Long createdBy;
}
