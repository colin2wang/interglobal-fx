package com.globalfx.report.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;

@Mapper
public interface DashboardMapper {

    @Select("SELECT COUNT(*) FROM crm_customer WHERE is_deleted = 0")
    long countTotalCustomers();

    @Select("SELECT COUNT(*) FROM acc_account WHERE is_deleted = 0 AND status = 1")
    long countActiveAccounts();

    @Select("SELECT COUNT(*) FROM trd_order WHERE is_deleted = 0")
    long countTotalOrders();

    @Select("SELECT COALESCE(SUM(balance), 0) FROM acc_account WHERE is_deleted = 0")
    BigDecimal sumTotalBalance();

    @Select("SELECT COUNT(*) FROM pos_position WHERE is_deleted = 0 AND status = 1")
    long countOpenPositions();

    @Select("SELECT COALESCE(SUM(unrealized_pnl), 0) FROM pos_position WHERE is_deleted = 0 AND status = 1")
    BigDecimal sumUnrealizedPnl();
}
