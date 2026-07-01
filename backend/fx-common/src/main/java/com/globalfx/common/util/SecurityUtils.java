package com.globalfx.common.util;

public final class SecurityUtils {

    private SecurityUtils() {}

    private static final ThreadLocal<Long> CURRENT_USER_ID = new ThreadLocal<>();
    private static final ThreadLocal<String> CURRENT_USERNAME = new ThreadLocal<>();
    private static final ThreadLocal<Long> CURRENT_TENANT_ID = new ThreadLocal<>();
    private static final ThreadLocal<Long> CURRENT_ACCOUNT_ID = new ThreadLocal<>();

    public static Long getCurrentUserId() { return CURRENT_USER_ID.get(); }
    public static String getCurrentUsername() { return CURRENT_USERNAME.get(); }
    public static Long getCurrentTenantId() { return CURRENT_TENANT_ID.get(); }
    public static Long getCurrentAccountId() { return CURRENT_ACCOUNT_ID.get(); }

    public static void setCurrentUserId(Long userId) { CURRENT_USER_ID.set(userId); }
    public static void setCurrentUsername(String username) { CURRENT_USERNAME.set(username); }
    public static void setCurrentTenantId(Long tenantId) { CURRENT_TENANT_ID.set(tenantId); }
    public static void setCurrentAccountId(Long accountId) { CURRENT_ACCOUNT_ID.set(accountId); }

    public static void clear() {
        CURRENT_USER_ID.remove();
        CURRENT_USERNAME.remove();
        CURRENT_TENANT_ID.remove();
        CURRENT_ACCOUNT_ID.remove();
    }
}
