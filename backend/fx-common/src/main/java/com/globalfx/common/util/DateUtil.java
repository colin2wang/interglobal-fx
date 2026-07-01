package com.globalfx.common.util;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public final class DateUtil {

    private DateUtil() {}

    public static final String DATE_PATTERN = "yyyy-MM-dd";
    public static final String TIME_PATTERN = "HH:mm:ss";
    public static final String DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String DATETIME_PATTERN_MS = "yyyy-MM-dd HH:mm:ss.SSS";

    private static final ZoneId SHANGHAI_ZONE = ZoneId.of("Asia/Shanghai");
    private static final ZoneId UTC_ZONE = ZoneId.of("UTC");

    public static LocalDateTime now() { return LocalDateTime.now(); }
    public static LocalDate nowDate() { return LocalDate.now(); }
    public static long nowTimestamp() { return System.currentTimeMillis(); }

    public static String format(LocalDateTime date, String pattern) {
        if (date == null) return null;
        return date.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String formatDateTime(LocalDateTime date) {
        return format(date, DATETIME_PATTERN);
    }

    public static String formatDate(LocalDate date) {
        if (date == null) return null;
        return date.format(DateTimeFormatter.ofPattern(DATE_PATTERN));
    }

    public static LocalDateTime parse(String dateStr, String pattern) {
        return LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern(pattern));
    }

    public static LocalDateTime addDays(LocalDateTime date, long days) { return date.plusDays(days); }
    public static LocalDateTime addHours(LocalDateTime date, long hours) { return date.plusHours(hours); }
    public static LocalDateTime addMinutes(LocalDateTime date, long minutes) { return date.plusMinutes(minutes); }

    public static long betweenDays(LocalDateTime start, LocalDateTime end) {
        return ChronoUnit.DAYS.between(start, end);
    }

    public static boolean isToday(LocalDateTime date) {
        return date.toLocalDate().equals(LocalDate.now());
    }

    public static LocalDateTime startOfDay(LocalDateTime date) { return date.toLocalDate().atStartOfDay(); }
    public static LocalDateTime endOfDay(LocalDateTime date) { return date.toLocalDate().atTime(LocalTime.MAX); }

    public static LocalDateTime toUtc(LocalDateTime date) {
        return date.atZone(SHANGHAI_ZONE).withZoneSameInstant(UTC_ZONE).toLocalDateTime();
    }

    public static LocalDateTime toShanghai(LocalDateTime date) {
        return date.atZone(UTC_ZONE).withZoneSameInstant(SHANGHAI_ZONE).toLocalDateTime();
    }

    public static long toTimestamp(LocalDateTime date) {
        return date.atZone(SHANGHAI_ZONE).toInstant().toEpochMilli();
    }

    public static LocalDateTime of(long timestamp) {
        return Instant.ofEpochMilli(timestamp).atZone(SHANGHAI_ZONE).toLocalDateTime();
    }
}
