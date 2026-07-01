package com.globalfx.report.service;

import com.globalfx.report.dto.CustomerReportDTO;
import com.globalfx.report.vo.CustomerReportVO;

public interface CustomerReportService {

    CustomerReportVO getCustomerReport(CustomerReportDTO dto);
}
