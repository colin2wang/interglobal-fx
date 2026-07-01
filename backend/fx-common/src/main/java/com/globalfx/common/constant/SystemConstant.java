package com.globalfx.common.constant;

public final class SystemConstant {
    private SystemConstant() {}
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_TENANT_ID = "X-Tenant-Id";
    public static final String HEADER_REQUEST_ID = "X-Request-Id";
    public static final String HEADER_LOCALE = "X-Locale";
    public static final String HEADER_TIME_ZONE = "X-Time-Zone";
    public static final int DEFAULT_PAGE_SIZE = 20;
    public static final int MAX_PAGE_SIZE = 100;
    public static final long ACCESS_TOKEN_EXPIRATION = 1800000L;
    public static final long REFRESH_TOKEN_EXPIRATION = 604800000L;
}
