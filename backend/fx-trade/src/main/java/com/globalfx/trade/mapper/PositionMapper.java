package com.globalfx.trade.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.globalfx.trade.entity.Position;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PositionMapper extends BaseMapper<Position> {

    @Select("SELECT p.*, s.symbol as symbol_name FROM pos_position p LEFT JOIN sym_symbol s ON p.symbol_id = s.id WHERE p.account_id = #{accountId} AND p.status = 1 AND p.is_deleted = 0 ORDER BY p.open_time DESC")
    List<Position> selectOpenPositionsByAccountId(@Param("accountId") Long accountId);
}
