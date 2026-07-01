package com.globalfx.clearing.service.impl;

import com.globalfx.clearing.service.SwapCalculationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Slf4j
@Service
public class SwapCalculationServiceImpl implements SwapCalculationService {

    private static final int SWAP_TRIPLE_DAY = 3;
    private static final int CONTRACT_SIZE = 100000;

    @Override
    public BigDecimal calculateSwap(Long symbolId, Integer positionSide, BigDecimal lotSize, int holdingDays) {
        BigDecimal swapRate = getSwapRate(symbolId, positionSide);

        int swapDays = holdingDays;
        if (holdingDays >= 3) {
            swapDays = holdingDays + 1;
        }

        BigDecimal swap = lotSize.multiply(new BigDecimal(CONTRACT_SIZE))
                .multiply(swapRate)
                .multiply(new BigDecimal(swapDays))
                .setScale(8, RoundingMode.HALF_UP);

        if (positionSide == 2) {
            swap = swap.negate();
        }

        return swap;
    }

    private BigDecimal getSwapRate(Long symbolId, Integer positionSide) {
        return new BigDecimal("0.00001");
    }
}
