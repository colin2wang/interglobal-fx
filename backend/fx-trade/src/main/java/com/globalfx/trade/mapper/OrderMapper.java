package com.globalfx.trade.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.globalfx.trade.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {

    @Select("SELECT o.*, s.symbol as symbol_name FROM trd_order o LEFT JOIN sym_symbol s ON o.symbol_id = s.id WHERE o.account_id = #{accountId} AND o.is_deleted = 0 ORDER BY o.created_time DESC")
    List<Order> selectByAccountId(@Param("accountId") Long accountId);
}
