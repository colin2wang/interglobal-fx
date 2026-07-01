package com.globalfx.trade.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.globalfx.trade.entity.Symbol;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SymbolMapper extends BaseMapper<Symbol> {
}
