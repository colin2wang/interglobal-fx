package com.globalfx.trade.controller;

import com.globalfx.common.result.PageResult;
import com.globalfx.common.result.Result;
import com.globalfx.trade.dto.CreateOrderDTO;
import com.globalfx.trade.service.OrderService;
import com.globalfx.trade.vo.OrderVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "订单管理")
@RestController
@RequestMapping("/api/v1/trade/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "创建订单")
    @PostMapping
    public Result<OrderVO> create(@Valid @RequestBody CreateOrderDTO dto) {
        OrderVO order = orderService.createOrder(dto);
        return Result.success(order);
    }

    @Operation(summary = "分页查询订单")
    @GetMapping
    public Result<PageResult<OrderVO>> list(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "20") int pageSize,
            @RequestParam(required = false) Long accountId,
            @RequestParam(required = false) Integer status) {
        PageResult<OrderVO> result = orderService.pageList(pageNum, pageSize, accountId, status);
        return Result.success(result);
    }

    @Operation(summary = "获取订单详情")
    @GetMapping("/{id}")
    public Result<OrderVO> getDetail(@PathVariable Long id) {
        OrderVO order = orderService.getDetail(id);
        return Result.success(order);
    }

    @Operation(summary = "取消订单")
    @DeleteMapping("/{id}")
    public Result<Void> cancel(@PathVariable Long id) {
        orderService.cancelOrder(id);
        return Result.success();
    }
}
