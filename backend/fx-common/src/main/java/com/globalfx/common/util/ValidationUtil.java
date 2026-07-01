package com.globalfx.common.util;

import com.globalfx.common.exception.BusinessException;
import java.math.BigDecimal;
import java.util.regex.Pattern;

public final class ValidationUtil {

    private ValidationUtil() {}

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\+?[1-9]\\d{6,14}$");

    public static void notNull(Object obj, String message) {
        if (obj == null) throw new BusinessException(message);
    }

    public static void notBlank(String str, String message) {
        if (str == null || str.isBlank()) throw new BusinessException(message);
    }

    public static void isTrue(boolean expression, String message) {
        if (!expression) throw new BusinessException(message);
    }

    public static void isFalse(boolean expression, String message) {
        if (expression) throw new BusinessException(message);
    }

    public static boolean isEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    public static boolean isPhone(String phone) {
        return phone != null && PHONE_PATTERN.matcher(phone).matches();
    }

    public static void isPositive(BigDecimal num, String message) {
        notNull(num, message);
        if (num.compareTo(BigDecimal.ZERO) <= 0) throw new BusinessException(message);
    }

    public static void isBetween(BigDecimal num, BigDecimal min, BigDecimal max, String message) {
        notNull(num, message);
        if (num.compareTo(min) < 0 || num.compareTo(max) > 0) throw new BusinessException(message);
    }
}
