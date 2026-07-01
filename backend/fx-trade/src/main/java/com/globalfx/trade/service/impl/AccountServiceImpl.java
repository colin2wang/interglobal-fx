package com.globalfx.trade.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.globalfx.common.exception.BusinessException;
import com.globalfx.common.result.PageResult;
import com.globalfx.common.result.ResultCode;
import com.globalfx.trade.entity.Account;
import com.globalfx.trade.mapper.AccountMapper;
import com.globalfx.trade.service.AccountService;
import com.globalfx.trade.vo.AccountVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements AccountService {

    private final AccountMapper accountMapper;

    @Override
    public PageResult<AccountVO> pageList(int pageNum, int pageSize, Long customerId) {
        Page<Account> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Account> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(customerId != null, Account::getCustomerId, customerId)
                .orderByDesc(Account::getCreatedTime);
        Page<Account> result = accountMapper.selectPage(page, wrapper);

        List<AccountVO> voList = result.getRecords().stream()
                .map(this::toVO)
                .collect(Collectors.toList());

        return new PageResult<>(voList, result.getTotal(), pageNum, pageSize);
    }

    @Override
    public AccountVO getDetail(Long id) {
        Account account = accountMapper.selectById(id);
        if (account == null) {
            throw new BusinessException(ResultCode.ACCOUNT_NOT_FOUND);
        }
        return toVO(account);
    }

    private AccountVO toVO(Account account) {
        AccountVO vo = new AccountVO();
        BeanUtils.copyProperties(account, vo);
        return vo;
    }
}
