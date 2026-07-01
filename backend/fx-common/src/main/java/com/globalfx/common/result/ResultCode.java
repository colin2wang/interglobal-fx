package com.globalfx.common.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultCode {

    SUCCESS(200, "success"),
    PARAM_ERROR(40001, "参数校验失败"),
    BALANCE_NOT_SUFFICIENT(40002, "余额不足"),
    MARGIN_NOT_SUFFICIENT(40003, "保证金不足"),
    EXCEED_TRADE_LIMIT(40004, "超出交易限额"),
    PRICE_OUT_OF_RANGE(40005, "价格超出范围"),
    SYMBOL_NOT_TRADEABLE(40006, "品种不可交易"),
    ORDER_TYPE_NOT_SUPPORTED(40007, "订单类型不支持"),
    TOKEN_EXPIRED(40101, "Token已过期"),
    TOKEN_INVALID(40102, "Token无效"),
    REFRESH_TOKEN_INVALID(40103, "刷新Token失效"),
    PERMISSION_DENIED(40301, "无权限访问"),
    ACCOUNT_FROZEN(40302, "账户已被冻结"),
    KYC_NOT_APPROVED(40303, "KYC未通过"),
    DEMO_ACCOUNT_RESTRICTED(40304, "模拟账户不允许此操作"),
    NOT_FOUND(40401, "资源不存在"),
    ACCOUNT_NOT_FOUND(40402, "账户不存在"),
    ORDER_NOT_FOUND(40403, "订单不存在"),
    POSITION_NOT_FOUND(40404, "持仓不存在"),
    SYMBOL_NOT_FOUND(40405, "品种不存在"),
    DATA_CONFLICT(40901, "数据冲突/重复"),
    ORDER_ALREADY_FILLED(40902, "订单已成交"),
    POSITION_LOCKED(40903, "持仓已被锁定"),
    DUPLICATE_ORDER(40904, "重复下单"),
    RISK_REJECTED(42201, "风控规则拒绝"),
    ANOMALY_DETECTED(42202, "异常交易检测"),
    SANCTIONS_HIT(42203, "制裁名单命中"),
    SYSTEM_ERROR(50001, "服务器内部错误"),
    DB_ERROR(50002, "数据库错误"),
    CACHE_ERROR(50003, "缓存错误"),
    MQ_ERROR(50004, "消息队列错误"),
    SERVICE_UNAVAILABLE(50301, "服务暂不可用"),
    SYSTEM_MAINTENANCE(50302, "系统维护中");

    private final int code;
    private final String message;
}
