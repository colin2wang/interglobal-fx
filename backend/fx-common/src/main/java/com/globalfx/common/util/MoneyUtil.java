package com.globalfx.common.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public final class MoneyUtil {

    private MoneyUtil() {}

    public static final int DEFAULT_SCALE = 8;
    public static final int DISPLAY_SCALE = 2;

    public static BigDecimal add(BigDecimal a, BigDecimal b) { return a.add(b); }
    public static BigDecimal subtract(BigDecimal a, BigDecimal b) { return a.subtract(b); }
    public static BigDecimal multiply(BigDecimal a, BigDecimal b) { return a.multiply(b); }
    public static BigDecimal divide(BigDecimal a, BigDecimal b, int scale) {
        if (b.compareTo(BigDecimal.ZERO) == 0) throw new ArithmeticException("Division by zero");
        return a.divide(b, scale, RoundingMode.HALF_UP);
    }
    public static int compare(BigDecimal a, BigDecimal b) { return a.compareTo(b); }
    public static boolean isPositive(BigDecimal amount) { return amount.compareTo(BigDecimal.ZERO) > 0; }
    public static boolean isNegative(BigDecimal amount) { return amount.compareTo(BigDecimal.ZERO) < 0; }
    public static boolean isZero(BigDecimal amount) { return amount.compareTo(BigDecimal.ZERO) == 0; }
    public static BigDecimal abs(BigDecimal amount) { return amount.abs(); }
    public static BigDecimal negate(BigDecimal amount) { return amount.negate(); }
    public static BigDecimal max(BigDecimal a, BigDecimal b) { return a.max(b); }
    public static BigDecimal min(BigDecimal a, BigDecimal b) { return a.min(b); }

    public static String format(BigDecimal amount, int scale) {
        if (amount == null) return "0";
        DecimalFormat df = new DecimalFormat("#,##0." + "0".repeat(scale));
        return df.format(amount.setScale(scale, RoundingMode.HALF_UP));
    }

    public static String formatDefault(BigDecimal amount) {
        return format(amount, DISPLAY_SCALE);
    }

    public static BigDecimal parse(String amount) {
        if (amount == null || amount.isBlank()) return BigDecimal.ZERO;
        return new BigDecimal(amount.trim());
    }

    public static BigDecimal round(BigDecimal amount, int scale) {
        return amount.setScale(scale, RoundingMode.HALF_UP);
    }

    public static BigDecimal calculatePnL(BigDecimal openPrice, BigDecimal closePrice,
                                           BigDecimal lotSize, BigDecimal contractSize) {
        return closePrice.subtract(openPrice).multiply(lotSize).multiply(contractSize);
    }

    public static BigDecimal calculateMargin(BigDecimal price, BigDecimal lotSize,
                                              BigDecimal contractSize, BigDecimal leverage) {
        return price.multiply(lotSize).multiply(contractSize).divide(leverage, DEFAULT_SCALE, RoundingMode.HALF_UP);
    }

    public static BigDecimal calculateSwap(BigDecimal lotSize, BigDecimal swapRate, int days) {
        return lotSize.multiply(swapRate).multiply(BigDecimal.valueOf(days));
    }
}
