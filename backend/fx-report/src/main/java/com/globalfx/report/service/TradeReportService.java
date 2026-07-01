package com.globalfx.report.service;

import com.globalfx.report.dto.TradeReportDTO;
import com.globalfx.report.vo.TradeReportVO;

public interface TradeReportService {

    TradeReportVO getTradeReport(TradeReportDTO dto);
}
