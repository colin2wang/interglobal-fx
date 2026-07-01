package com.globalfx.report.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;

@Mapper
public interface TradeReportMapper {

    @Select("SELECT COUNT(*) FROM trd_order WHERE is_deleted = 0")
    long countTotalOrders();

    @Select("SELECT COALESCE(SUM(filled_lot), 0) FROM trd_order WHERE is_deleted = 0 AND status = 3")
    BigDecimal sumTotalVolume();

    @Select("SELECT COALESCE(SUM(commission), 0) FROM pos_position WHERE is_deleted = 0")
    BigDecimal sumTotalCommission();

    @Select("SELECT COALESCE(SUM(swap_amount), 0) FROM pos_position WHERE is_deleted = 0")
    BigDecimal sumTotalSwap();

    @Select("SELECT COALESCE(SUM(realized_pnl), 0) FROM pos_position WHERE is_deleted = 0")
    BigDecimal sumTotalRealizedPnl();
}
