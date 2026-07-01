package com.globalfx.crm.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TicketDTO {

    private Long id;

    @NotNull(message = "客户ID不能为空")
    private Long customerId;

    @NotNull(message = "工单分类不能为空")
    private Integer category;

    private Integer priority;

    @NotBlank(message = "标题不能为空")
    private String title;

    @NotBlank(message = "内容不能为空")
    private String content;

    private Long accountId;
    private Long assignedTo;
    private String remark;
}
