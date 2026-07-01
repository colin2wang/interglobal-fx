package com.globalfx.trade.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.globalfx.trade.dto.DepositDTO;
import com.globalfx.trade.dto.WithdrawDTO;
import com.globalfx.trade.entity.Wallet;
import com.globalfx.trade.vo.WalletVO;

import java.math.BigDecimal;

public interface WalletService extends IService<Wallet> {

    WalletVO getBalance(Long walletId);

    void deposit(DepositDTO dto);

    void withdraw(WithdrawDTO dto);
}
