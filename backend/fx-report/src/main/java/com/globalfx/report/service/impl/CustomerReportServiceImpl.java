package com.globalfx.report.service.impl;

import com.globalfx.report.dto.CustomerReportDTO;
import com.globalfx.report.mapper.CustomerReportMapper;
import com.globalfx.report.service.CustomerReportService;
import com.globalfx.report.vo.CustomerReportVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerReportServiceImpl implements CustomerReportService {

    private final CustomerReportMapper customerReportMapper;

    @Override
    public CustomerReportVO getCustomerReport(CustomerReportDTO dto) {
        long totalCustomers = customerReportMapper.countTotalCustomers();
        long newCustomers = customerReportMapper.countNewCustomers();
        long activeCustomers = customerReportMapper.countActiveCustomers();
        BigDecimal totalDeposits = customerReportMapper.sumTotalDeposits();
        BigDecimal totalWithdrawals = customerReportMapper.sumTotalWithdrawals();
        long totalTrades = customerReportMapper.sumTotalTrades();

        BigDecimal avgDeposit = totalCustomers > 0 ?
                totalDeposits.divide(new BigDecimal(totalCustomers), 2, RoundingMode.HALF_UP) :
                BigDecimal.ZERO;
        BigDecimal avgTradesPerCustomer = totalCustomers > 0 ?
                new BigDecimal(totalTrades).divide(new BigDecimal(totalCustomers), 2, RoundingMode.HALF_UP) :
                BigDecimal.ZERO;

        return CustomerReportVO.builder()
                .totalCustomers(totalCustomers)
                .newCustomers(newCustomers)
                .activeCustomers(activeCustomers)
                .totalDeposits(totalDeposits)
                .totalWithdrawals(totalWithdrawals)
                .totalTrades(totalTrades)
                .avgDeposit(avgDeposit)
                .avgTradesPerCustomer(avgTradesPerCustomer)
                .build();
    }
}
