package com.globalfx.trade.controller;

import com.globalfx.common.result.PageResult;
import com.globalfx.common.result.Result;
import com.globalfx.trade.service.AccountService;
import com.globalfx.trade.vo.AccountVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "账户管理")
@RestController
@RequestMapping("/api/v1/trade/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @Operation(summary = "分页查询账户")
    @GetMapping
    public Result<PageResult<AccountVO>> list(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "20") int pageSize,
            @RequestParam(required = false) Long customerId) {
        PageResult<AccountVO> result = accountService.pageList(pageNum, pageSize, customerId);
        return Result.success(result);
    }

    @Operation(summary = "获取账户详情")
    @GetMapping("/{id}")
    public Result<AccountVO> getDetail(@PathVariable Long id) {
        AccountVO account = accountService.getDetail(id);
        return Result.success(account);
    }
}
