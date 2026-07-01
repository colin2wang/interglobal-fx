package com.globalfx.report.service.impl;

import com.globalfx.report.dto.TradeReportDTO;
import com.globalfx.report.mapper.TradeReportMapper;
import com.globalfx.report.service.TradeReportService;
import com.globalfx.report.vo.TradeReportVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Slf4j
@Service
@RequiredArgsConstructor
public class TradeReportServiceImpl implements TradeReportService {

    private final TradeReportMapper tradeReportMapper;

    @Override
    public TradeReportVO getTradeReport(TradeReportDTO dto) {
        long totalOrders = tradeReportMapper.countTotalOrders();
        BigDecimal totalVolume = tradeReportMapper.sumTotalVolume();
        BigDecimal totalCommission = tradeReportMapper.sumTotalCommission();
        BigDecimal totalSwap = tradeReportMapper.sumTotalSwap();
        BigDecimal totalRealizedPnl = tradeReportMapper.sumTotalRealizedPnl();

        long winningTrades = totalRealizedPnl.compareTo(BigDecimal.ZERO) > 0 ? totalOrders / 2 : 0;
        long losingTrades = totalOrders - winningTrades;
        BigDecimal winRate = totalOrders > 0 ?
                new BigDecimal(winningTrades).multiply(new BigDecimal(100))
                        .divide(new BigDecimal(totalOrders), 2, RoundingMode.HALF_UP) :
                BigDecimal.ZERO;

        return TradeReportVO.builder()
                .totalOrders(totalOrders)
                .totalVolume(totalVolume.longValue())
                .totalTurnover(totalVolume)
                .totalCommission(totalCommission)
                .totalSwap(totalSwap)
                .totalRealizedPnl(totalRealizedPnl)
                .winningTrades(winningTrades)
                .losingTrades(losingTrades)
                .winRate(winRate)
                .avgProfit(BigDecimal.ZERO)
                .avgLoss(BigDecimal.ZERO)
                .profitFactor(BigDecimal.ZERO)
                .build();
    }
}
