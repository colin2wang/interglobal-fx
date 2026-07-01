package com.globalfx.trade.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.globalfx.common.exception.BusinessException;
import com.globalfx.common.result.ResultCode;
import com.globalfx.trade.dto.DepositDTO;
import com.globalfx.trade.dto.WithdrawDTO;
import com.globalfx.trade.entity.Wallet;
import com.globalfx.trade.entity.WalletTransaction;
import com.globalfx.trade.mapper.WalletMapper;
import com.globalfx.trade.mapper.WalletTransactionMapper;
import com.globalfx.trade.service.WalletService;
import com.globalfx.trade.vo.WalletVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class WalletServiceImpl extends ServiceImpl<WalletMapper, Wallet> implements WalletService {

    private final WalletMapper walletMapper;
    private final WalletTransactionMapper walletTransactionMapper;

    @Override
    public WalletVO getBalance(Long walletId) {
        Wallet wallet = walletMapper.selectById(walletId);
        if (wallet == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "钱包不存在");
        }
        WalletVO vo = new WalletVO();
        BeanUtils.copyProperties(wallet, vo);
        return vo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deposit(DepositDTO dto) {
        Wallet wallet = walletMapper.selectById(dto.getWalletId());
        if (wallet == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "钱包不存在");
        }

        BigDecimal balanceBefore = wallet.getBalance();
        BigDecimal balanceAfter = balanceBefore.add(dto.getAmount());

        wallet.setBalance(balanceAfter);
        wallet.setTotalIn(wallet.getTotalIn().add(dto.getAmount()));
        wallet.setVersion(wallet.getVersion() + 1);
        walletMapper.updateById(wallet);

        WalletTransaction txn = new WalletTransaction();
        txn.setWalletId(wallet.getId());
        txn.setAccountId(wallet.getAccountId());
        txn.setCurrency(wallet.getCurrency());
        txn.setTxnType(1);
        txn.setDirection(1);
        txn.setAmount(dto.getAmount());
        txn.setBalanceBefore(balanceBefore);
        txn.setBalanceAfter(balanceAfter);
        txn.setFrozenBefore(wallet.getFrozenBalance());
        txn.setFrozenAfter(wallet.getFrozenBalance());
        txn.setStatus(1);
        txn.setRemark(dto.getRemark());
        txn.setCreatedTime(LocalDateTime.now());
        walletTransactionMapper.insert(txn);

        log.info("Deposit success: walletId={}, amount={}", wallet.getId(), dto.getAmount());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void withdraw(WithdrawDTO dto) {
        Wallet wallet = walletMapper.selectById(dto.getWalletId());
        if (wallet == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "钱包不存在");
        }

        if (wallet.getBalance().compareTo(dto.getAmount()) < 0) {
            throw new BusinessException(ResultCode.BALANCE_NOT_SUFFICIENT);
        }

        BigDecimal balanceBefore = wallet.getBalance();
        BigDecimal balanceAfter = balanceBefore.subtract(dto.getAmount());

        wallet.setBalance(balanceAfter);
        wallet.setTotalOut(wallet.getTotalOut().add(dto.getAmount()));
        wallet.setVersion(wallet.getVersion() + 1);
        walletMapper.updateById(wallet);

        WalletTransaction txn = new WalletTransaction();
        txn.setWalletId(wallet.getId());
        txn.setAccountId(wallet.getAccountId());
        txn.setCurrency(wallet.getCurrency());
        txn.setTxnType(2);
        txn.setDirection(2);
        txn.setAmount(dto.getAmount());
        txn.setBalanceBefore(balanceBefore);
        txn.setBalanceAfter(balanceAfter);
        txn.setFrozenBefore(wallet.getFrozenBalance());
        txn.setFrozenAfter(wallet.getFrozenBalance());
        txn.setStatus(1);
        txn.setRemark(dto.getRemark());
        txn.setCreatedTime(LocalDateTime.now());
        walletTransactionMapper.insert(txn);

        log.info("Withdraw success: walletId={}, amount={}", wallet.getId(), dto.getAmount());
    }
}
