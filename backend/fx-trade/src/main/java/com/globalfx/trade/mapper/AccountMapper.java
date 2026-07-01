package com.globalfx.trade.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.globalfx.trade.entity.Account;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AccountMapper extends BaseMapper<Account> {
}
