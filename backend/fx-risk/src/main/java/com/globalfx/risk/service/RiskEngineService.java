package com.globalfx.risk.service;

import com.globalfx.risk.dto.OrderCheckDTO;
import com.globalfx.risk.vo.RiskCheckResult;

public interface RiskEngineService {

    RiskCheckResult checkOrder(OrderCheckDTO dto);
}
