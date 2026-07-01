package com.globalfx.trade.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.globalfx.common.result.PageResult;
import com.globalfx.trade.entity.Account;
import com.globalfx.trade.vo.AccountVO;

public interface AccountService extends IService<Account> {

    PageResult<AccountVO> pageList(int pageNum, int pageSize, Long customerId);

    AccountVO getDetail(Long id);
}
