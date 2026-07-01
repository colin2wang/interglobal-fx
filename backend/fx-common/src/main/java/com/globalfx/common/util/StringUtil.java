package com.globalfx.common.util;

public final class StringUtil {

    private StringUtil() {}

    public static boolean isEmpty(String str) { return str == null || str.isEmpty(); }
    public static boolean isNotEmpty(String str) { return !isEmpty(str); }
    public static boolean isBlank(String str) { return str == null || str.isBlank(); }
    public static boolean isNotBlank(String str) { return !isBlank(str); }

    public static String defaultIfEmpty(String str, String defaultStr) {
        return isEmpty(str) ? defaultStr : str;
    }

    public static String defaultIfBlank(String str, String defaultStr) {
        return isBlank(str) ? defaultStr : str;
    }

    public static String trim(String str) { return str == null ? null : str.trim(); }

    public static String mask(String str, int start, int end, char maskChar) {
        if (str == null || str.length() <= start) return str;
        int actualEnd = Math.min(end, str.length());
        StringBuilder sb = new StringBuilder(str);
        for (int i = start; i < actualEnd; i++) {
            sb.setCharAt(i, maskChar);
        }
        return sb.toString();
    }
}
