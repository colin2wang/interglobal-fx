package com.globalfx.risk.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class HandleEventDTO {

    @NotNull(message = "事件ID不能为空")
    private Long eventId;

    @NotNull(message = "处理结果不能为空")
    private String operateResult;

    private Long operatorId;
}
