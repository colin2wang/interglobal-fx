package com.globalfx.crm.controller;

import com.globalfx.common.result.PageResult;
import com.globalfx.common.result.Result;
import com.globalfx.crm.dto.TicketDTO;
import com.globalfx.crm.dto.TicketReplyDTO;
import com.globalfx.crm.service.TicketService;
import com.globalfx.crm.vo.TicketVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "工单管理")
@RestController
@RequestMapping("/api/v1/crm/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @Operation(summary = "分页查询工单")
    @GetMapping
    public Result<PageResult<TicketVO>> list(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "20") int pageSize,
            @RequestParam(required = false) Long customerId,
            @RequestParam(required = false) Integer status) {
        PageResult<TicketVO> result = ticketService.pageList(pageNum, pageSize, customerId, status);
        return Result.success(result);
    }

    @Operation(summary = "获取工单详情")
    @GetMapping("/{id}")
    public Result<TicketVO> getDetail(@PathVariable Long id) {
        TicketVO vo = ticketService.getDetail(id);
        return Result.success(vo);
    }

    @Operation(summary = "创建工单")
    @PostMapping
    public Result<Void> create(@Valid @RequestBody TicketDTO dto) {
        ticketService.createTicket(dto);
        return Result.success();
    }

    @Operation(summary = "更新工单")
    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable Long id, @Valid @RequestBody TicketDTO dto) {
        dto.setId(id);
        ticketService.updateTicket(dto);
        return Result.success();
    }

    @Operation(summary = "删除工单")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        ticketService.deleteTicket(id);
        return Result.success();
    }

    @Operation(summary = "工单回复")
    @PostMapping("/reply")
    public Result<Void> reply(@Valid @RequestBody TicketReplyDTO dto) {
        ticketService.reply(dto);
        return Result.success();
    }
}
