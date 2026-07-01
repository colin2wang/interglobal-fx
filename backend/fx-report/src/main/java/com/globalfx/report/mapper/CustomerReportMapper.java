package com.globalfx.report.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;

@Mapper
public interface CustomerReportMapper {

    @Select("SELECT COUNT(*) FROM crm_customer WHERE is_deleted = 0")
    long countTotalCustomers();

    @Select("SELECT COUNT(*) FROM crm_customer WHERE is_deleted = 0 AND created_time >= CURRENT_DATE - INTERVAL '30 days'")
    long countNewCustomers();

    @Select("SELECT COUNT(*) FROM crm_customer WHERE is_deleted = 0 AND status = 2")
    long countActiveCustomers();

    @Select("SELECT COALESCE(SUM(total_deposit), 0) FROM crm_customer WHERE is_deleted = 0")
    BigDecimal sumTotalDeposits();

    @Select("SELECT COALESCE(SUM(total_withdrawal), 0) FROM crm_customer WHERE is_deleted = 0")
    BigDecimal sumTotalWithdrawals();

    @Select("SELECT COALESCE(SUM(total_trades), 0) FROM crm_customer WHERE is_deleted = 0")
    long sumTotalTrades();
}
