package com.globalfx.trade.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.globalfx.trade.entity.WalletTransaction;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WalletTransactionMapper extends BaseMapper<WalletTransaction> {
}
