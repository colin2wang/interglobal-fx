package com.globalfx.clearing.service;

import java.math.BigDecimal;

public interface SwapCalculationService {

    BigDecimal calculateSwap(Long symbolId, Integer positionSide, BigDecimal lotSize, int holdingDays);
}
