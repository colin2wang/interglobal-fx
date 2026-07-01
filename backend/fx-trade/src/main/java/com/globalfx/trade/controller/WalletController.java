package com.globalfx.trade.controller;

import com.globalfx.common.result.Result;
import com.globalfx.trade.dto.DepositDTO;
import com.globalfx.trade.dto.WithdrawDTO;
import com.globalfx.trade.service.WalletService;
import com.globalfx.trade.vo.WalletVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "钱包管理")
@RestController
@RequestMapping("/api/v1/trade/wallets")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    @Operation(summary = "查询余额")
    @GetMapping("/{id}/balance")
    public Result<WalletVO> getBalance(@PathVariable Long id) {
        WalletVO wallet = walletService.getBalance(id);
        return Result.success(wallet);
    }

    @Operation(summary = "入金")
    @PostMapping("/deposit")
    public Result<Void> deposit(@Valid @RequestBody DepositDTO dto) {
        walletService.deposit(dto);
        return Result.success();
    }

    @Operation(summary = "出金")
    @PostMapping("/withdraw")
    public Result<Void> withdraw(@Valid @RequestBody WithdrawDTO dto) {
        walletService.withdraw(dto);
        return Result.success();
    }
}
