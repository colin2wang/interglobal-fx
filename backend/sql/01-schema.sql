-- GlobalFX Trading Platform - Database Schema
-- PostgreSQL 15

-- ============================================
-- 1. 租户配置表
-- ============================================
CREATE TABLE cfg_tenant (
    id BIGSERIAL PRIMARY KEY,
    tenant_code VARCHAR(50) NOT NULL,
    tenant_name VARCHAR(100) NOT NULL,
    tenant_type SMALLINT NOT NULL DEFAULT 1,
    logo_url VARCHAR(500) DEFAULT NULL,
    domain VARCHAR(100) DEFAULT NULL,
    region_code VARCHAR(10) DEFAULT NULL,
    license_no VARCHAR(100) DEFAULT NULL,
    contact_email VARCHAR(100) DEFAULT NULL,
    contact_phone VARCHAR(30) DEFAULT NULL,
    config JSONB DEFAULT NULL,
    default_leverage DECIMAL(10,2) DEFAULT 30,
    default_currency VARCHAR(10) DEFAULT 'USD',
    status SMALLINT NOT NULL DEFAULT 1,
    expire_time TIMESTAMPTZ DEFAULT NULL,
    created_by BIGINT NOT NULL,
    created_time TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_by BIGINT DEFAULT NULL,
    updated_time TIMESTAMPTZ DEFAULT NULL,
    is_deleted SMALLINT NOT NULL DEFAULT 0,
    remark VARCHAR(500) DEFAULT NULL,
    UNIQUE(tenant_code)
);

-- ============================================
-- 2. 系统用户表
-- ============================================
CREATE TABLE usr_user (
    id BIGSERIAL PRIMARY KEY,
    user_no VARCHAR(30) NOT NULL,
    tenant_id BIGINT NOT NULL,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(200) NOT NULL,
    nickname VARCHAR(100) DEFAULT NULL,
    email VARCHAR(100) DEFAULT NULL,
    phone VARCHAR(30) DEFAULT NULL,
    avatar_url VARCHAR(500) DEFAULT NULL,
    dept_id BIGINT DEFAULT NULL,
    post VARCHAR(100) DEFAULT NULL,
    status SMALLINT NOT NULL DEFAULT 1,
    last_login_ip INET DEFAULT NULL,
    last_login_time TIMESTAMPTZ DEFAULT NULL,
    login_fail_count SMALLINT DEFAULT 0,
    lock_expire_time TIMESTAMPTZ DEFAULT NULL,
    password_expire_time TIMESTAMPTZ DEFAULT NULL,
    created_by BIGINT NOT NULL,
    created_time TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_by BIGINT DEFAULT NULL,
    updated_time TIMESTAMPTZ DEFAULT NULL,
    is_deleted SMALLINT NOT NULL DEFAULT 0,
    remark VARCHAR(500) DEFAULT NULL,
    UNIQUE(user_no),
    UNIQUE(username, tenant_id)
);

-- ============================================
-- 3. 角色表
-- ============================================
CREATE TABLE usr_role (
    id BIGSERIAL PRIMARY KEY,
    role_code VARCHAR(50) NOT NULL,
    role_name VARCHAR(100) NOT NULL,
    tenant_id BIGINT NOT NULL,
    data_scope SMALLINT NOT NULL DEFAULT 1,
    dept_id BIGINT DEFAULT NULL,
    status SMALLINT NOT NULL DEFAULT 1,
    sort_order INTEGER DEFAULT 0,
    created_by BIGINT NOT NULL,
    created_time TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_by BIGINT DEFAULT NULL,
    updated_time TIMESTAMPTZ DEFAULT NULL,
    is_deleted SMALLINT NOT NULL DEFAULT 0,
    remark VARCHAR(500) DEFAULT NULL,
    UNIQUE(role_code, tenant_id)
);

-- ============================================
-- 4. 权限/菜单表
-- ============================================
CREATE TABLE usr_permission (
    id BIGSERIAL PRIMARY KEY,
    parent_id BIGINT DEFAULT 0,
    permission_code VARCHAR(100) NOT NULL,
    permission_name VARCHAR(100) NOT NULL,
    permission_type SMALLINT NOT NULL DEFAULT 1,
    path VARCHAR(200) DEFAULT NULL,
    component VARCHAR(200) DEFAULT NULL,
    icon VARCHAR(100) DEFAULT NULL,
    sort_order INTEGER DEFAULT 0,
    visible SMALLINT DEFAULT 0,
    status SMALLINT NOT NULL DEFAULT 1,
    tenant_id BIGINT DEFAULT 0,
    created_by BIGINT NOT NULL,
    created_time TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_by BIGINT DEFAULT NULL,
    updated_time TIMESTAMPTZ DEFAULT NULL,
    is_deleted SMALLINT NOT NULL DEFAULT 0,
    remark VARCHAR(500) DEFAULT NULL,
    UNIQUE(permission_code)
);

-- ============================================
-- 5. 用户-角色关联表
-- ============================================
CREATE TABLE usr_user_role (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    tenant_id BIGINT NOT NULL,
    created_by BIGINT NOT NULL,
    created_time TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- ============================================
-- 6. 角色-权限关联表
-- ============================================
CREATE TABLE usr_role_permission (
    id BIGSERIAL PRIMARY KEY,
    role_id BIGINT NOT NULL,
    permission_id BIGINT NOT NULL,
    tenant_id BIGINT NOT NULL
);

-- ============================================
-- 7. 字典类型表
-- ============================================
CREATE TABLE dic_type (
    id BIGSERIAL PRIMARY KEY,
    dict_type VARCHAR(100) NOT NULL,
    dict_name VARCHAR(100) NOT NULL,
    tenant_id BIGINT NOT NULL DEFAULT 0,
    status SMALLINT NOT NULL DEFAULT 1,
    created_by BIGINT NOT NULL,
    created_time TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_by BIGINT DEFAULT NULL,
    updated_time TIMESTAMPTZ DEFAULT NULL,
    is_deleted SMALLINT NOT NULL DEFAULT 0,
    remark VARCHAR(500) DEFAULT NULL
);

-- ============================================
-- 8. 字典数据表
-- ============================================
CREATE TABLE dic_data (
    id BIGSERIAL PRIMARY KEY,
    dict_type VARCHAR(100) NOT NULL,
    dict_label VARCHAR(100) NOT NULL,
    dict_value VARCHAR(100) NOT NULL,
    sort_order INTEGER DEFAULT 0,
    css_class VARCHAR(100) DEFAULT NULL,
    list_class VARCHAR(100) DEFAULT NULL,
    is_default SMALLINT DEFAULT 0,
    status SMALLINT NOT NULL DEFAULT 1,
    tenant_id BIGINT NOT NULL DEFAULT 0,
    created_by BIGINT NOT NULL,
    created_time TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_by BIGINT DEFAULT NULL,
    updated_time TIMESTAMPTZ DEFAULT NULL,
    is_deleted SMALLINT NOT NULL DEFAULT 0,
    remark VARCHAR(500) DEFAULT NULL
);

CREATE INDEX idx_dic_type ON dic_data(dict_type);

-- ============================================
-- 9. 货币对表
-- ============================================
CREATE TABLE sym_symbol (
    id BIGSERIAL PRIMARY KEY,
    symbol VARCHAR(20) NOT NULL,
    symbol_name VARCHAR(100) NOT NULL,
    symbol_type SMALLINT NOT NULL DEFAULT 1,
    base_currency VARCHAR(10) NOT NULL,
    quote_currency VARCHAR(10) NOT NULL,
    precision SMALLINT NOT NULL DEFAULT 5,
    lot_size DECIMAL(15,5) NOT NULL DEFAULT 100000,
    min_lot DECIMAL(15,5) NOT NULL DEFAULT 0.01,
    max_lot DECIMAL(15,5) NOT NULL DEFAULT 100,
    lot_step DECIMAL(15,5) NOT NULL DEFAULT 0.01,
    min_distance_points INTEGER NOT NULL DEFAULT 10,
    trade_mode SMALLINT NOT NULL DEFAULT 1,
    contract_size DECIMAL(15,5) NOT NULL DEFAULT 100000,
    swap_long DECIMAL(10,5) NOT NULL DEFAULT 0,
    swap_short DECIMAL(10,5) NOT NULL DEFAULT 0,
    swap_mode SMALLINT NOT NULL DEFAULT 1,
    trading_hours VARCHAR(100) NOT NULL DEFAULT '00:00-24:00',
    status SMALLINT NOT NULL DEFAULT 1,
    tenant_id BIGINT NOT NULL,
    created_by BIGINT NOT NULL,
    created_time TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_by BIGINT DEFAULT NULL,
    updated_time TIMESTAMPTZ DEFAULT NULL,
    is_deleted SMALLINT NOT NULL DEFAULT 0,
    remark VARCHAR(500) DEFAULT NULL,
    UNIQUE(symbol, tenant_id)
);

CREATE INDEX idx_sym_type ON sym_symbol(symbol_type);

-- ============================================
-- 10. 客户表
-- ============================================
CREATE TABLE crm_customer (
    id BIGSERIAL PRIMARY KEY,
    customer_no VARCHAR(30) NOT NULL,
    tenant_id BIGINT NOT NULL,
    customer_type SMALLINT NOT NULL DEFAULT 1,
    first_name VARCHAR(100) DEFAULT NULL,
    last_name VARCHAR(100) DEFAULT NULL,
    full_name VARCHAR(200) NOT NULL,
    email VARCHAR(100) NOT NULL,
    phone VARCHAR(30) DEFAULT NULL,
    country VARCHAR(10) NOT NULL,
    city VARCHAR(100) DEFAULT NULL,
    address VARCHAR(500) DEFAULT NULL,
    birth_date DATE DEFAULT NULL,
    id_card_type SMALLINT DEFAULT NULL,
    id_card_no VARCHAR(100) DEFAULT NULL,
    risk_score INTEGER DEFAULT 0,
    risk_level SMALLINT NOT NULL DEFAULT 1,
    segment VARCHAR(50) DEFAULT NULL,
    source SMALLINT DEFAULT 1,
    ib_id BIGINT DEFAULT NULL,
    manager_id BIGINT DEFAULT NULL,
    status SMALLINT NOT NULL DEFAULT 1,
    last_active_time TIMESTAMPTZ DEFAULT NULL,
    total_deposit DECIMAL(20,8) DEFAULT 0,
    total_withdrawal DECIMAL(20,8) DEFAULT 0,
    total_trades INTEGER DEFAULT 0,
    created_by BIGINT NOT NULL,
    created_time TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_by BIGINT DEFAULT NULL,
    updated_time TIMESTAMPTZ DEFAULT NULL,
    is_deleted SMALLINT NOT NULL DEFAULT 0,
    remark VARCHAR(500) DEFAULT NULL,
    UNIQUE(customer_no),
    UNIQUE(email, tenant_id)
);

CREATE INDEX idx_crm_ib ON crm_customer(ib_id);
CREATE INDEX idx_crm_manager ON crm_customer(manager_id);
CREATE INDEX idx_crm_status ON crm_customer(status);

-- ============================================
-- 11. 账户表
-- ============================================
CREATE TABLE acc_account (
    id BIGSERIAL PRIMARY KEY,
    account_no VARCHAR(30) NOT NULL,
    tenant_id BIGINT NOT NULL,
    customer_id BIGINT NOT NULL,
    account_type SMALLINT NOT NULL DEFAULT 1,
    account_group VARCHAR(50) DEFAULT NULL,
    base_currency VARCHAR(10) NOT NULL DEFAULT 'USD',
    leverage DECIMAL(10,2) NOT NULL DEFAULT 30,
    balance DECIMAL(20,8) NOT NULL DEFAULT 0,
    equity DECIMAL(20,8) NOT NULL DEFAULT 0,
    margin DECIMAL(20,8) NOT NULL DEFAULT 0,
    free_margin DECIMAL(20,8) NOT NULL DEFAULT 0,
    margin_level DECIMAL(10,2) NOT NULL DEFAULT 0,
    credit DECIMAL(20,8) NOT NULL DEFAULT 0,
    unrealized_pnl DECIMAL(20,8) NOT NULL DEFAULT 0,
    realized_pnl DECIMAL(20,8) NOT NULL DEFAULT 0,
    deposit_amount DECIMAL(20,8) NOT NULL DEFAULT 0,
    withdraw_amount DECIMAL(20,8) NOT NULL DEFAULT 0,
    status SMALLINT NOT NULL DEFAULT 1,
    freeze_reason VARCHAR(500) DEFAULT NULL,
    ib_id BIGINT DEFAULT NULL,
    manager_id BIGINT DEFAULT NULL,
    platform SMALLINT NOT NULL DEFAULT 1,
    platform_account_id VARCHAR(100) DEFAULT NULL,
    region_code VARCHAR(10) DEFAULT NULL,
    risk_level SMALLINT NOT NULL DEFAULT 1,
    kyc_status SMALLINT NOT NULL DEFAULT 0,
    last_trade_time TIMESTAMPTZ DEFAULT NULL,
    last_login_time TIMESTAMPTZ DEFAULT NULL,
    version INTEGER NOT NULL DEFAULT 1,
    created_by BIGINT NOT NULL,
    created_time TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_by BIGINT DEFAULT NULL,
    updated_time TIMESTAMPTZ DEFAULT NULL,
    is_deleted SMALLINT NOT NULL DEFAULT 0,
    remark VARCHAR(500) DEFAULT NULL,
    UNIQUE(account_no)
);

CREATE INDEX idx_acc_customer ON acc_account(customer_id);
CREATE INDEX idx_acc_ib ON acc_account(ib_id);
CREATE INDEX idx_acc_status ON acc_account(status);

-- ============================================
-- 12. 钱包表
-- ============================================
CREATE TABLE wlt_wallet (
    id BIGSERIAL PRIMARY KEY,
    wallet_no UUID NOT NULL DEFAULT gen_random_uuid(),
    tenant_id BIGINT NOT NULL,
    account_id BIGINT NOT NULL,
    currency VARCHAR(10) NOT NULL,
    balance DECIMAL(20,8) NOT NULL DEFAULT 0,
    frozen_balance DECIMAL(20,8) NOT NULL DEFAULT 0,
    total_in DECIMAL(20,8) NOT NULL DEFAULT 0,
    total_out DECIMAL(20,8) NOT NULL DEFAULT 0,
    status SMALLINT NOT NULL DEFAULT 1,
    version INTEGER NOT NULL DEFAULT 1,
    created_by BIGINT NOT NULL,
    created_time TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_by BIGINT DEFAULT NULL,
    updated_time TIMESTAMPTZ DEFAULT NULL,
    is_deleted SMALLINT NOT NULL DEFAULT 0,
    remark VARCHAR(500) DEFAULT NULL,
    UNIQUE(wallet_no),
    UNIQUE(account_id, currency)
);

-- ============================================
-- 13. 钱包流水表
-- ============================================
CREATE TABLE wlt_transaction (
    id BIGSERIAL PRIMARY KEY,
    txn_no UUID NOT NULL DEFAULT gen_random_uuid(),
    tenant_id BIGINT NOT NULL,
    wallet_id BIGINT NOT NULL,
    account_id BIGINT NOT NULL,
    currency VARCHAR(10) NOT NULL,
    txn_type SMALLINT NOT NULL,
    direction SMALLINT NOT NULL,
    amount DECIMAL(20,8) NOT NULL,
    balance_before DECIMAL(20,8) NOT NULL,
    balance_after DECIMAL(20,8) NOT NULL,
    frozen_before DECIMAL(20,8) NOT NULL DEFAULT 0,
    frozen_after DECIMAL(20,8) NOT NULL DEFAULT 0,
    related_order_no UUID DEFAULT NULL,
    related_txn_no UUID DEFAULT NULL,
    status SMALLINT NOT NULL DEFAULT 1,
    reference_id VARCHAR(100) DEFAULT NULL,
    remark VARCHAR(500) DEFAULT NULL,
    created_time TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    UNIQUE(txn_no)
);

CREATE INDEX idx_wlt_wallet ON wlt_transaction(wallet_id);
CREATE INDEX idx_wlt_account ON wlt_transaction(account_id);
CREATE INDEX idx_wlt_created ON wlt_transaction(created_time);

-- ============================================
-- 14. 订单表
-- ============================================
CREATE TABLE trd_order (
    id BIGSERIAL PRIMARY KEY,
    order_no UUID NOT NULL DEFAULT gen_random_uuid(),
    tenant_id BIGINT NOT NULL,
    account_id BIGINT NOT NULL,
    symbol_id BIGINT NOT NULL,
    order_type SMALLINT NOT NULL,
    order_side SMALLINT NOT NULL,
    order_price DECIMAL(20,10) DEFAULT NULL,
    stop_price DECIMAL(20,10) DEFAULT NULL,
    lot_size DECIMAL(15,5) NOT NULL,
    filled_lot DECIMAL(15,5) NOT NULL DEFAULT 0,
    avg_price DECIMAL(20,10) DEFAULT NULL,
    order_price_show DECIMAL(20,10) DEFAULT NULL,
    slippage_points INTEGER NOT NULL DEFAULT 0,
    slippage_type SMALLINT NOT NULL DEFAULT 1,
    status SMALLINT NOT NULL DEFAULT 1,
    execution_type SMALLINT NOT NULL DEFAULT 1,
    time_in_force SMALLINT NOT NULL DEFAULT 1,
    expire_time TIMESTAMPTZ DEFAULT NULL,
    position_id BIGINT DEFAULT NULL,
    parent_order_id BIGINT DEFAULT NULL,
    lp_id BIGINT DEFAULT NULL,
    execution_mode SMALLINT NOT NULL DEFAULT 1,
    client_order_id VARCHAR(100) DEFAULT NULL,
    channel SMALLINT NOT NULL DEFAULT 1,
    ip_address INET DEFAULT NULL,
    order_text VARCHAR(500) DEFAULT NULL,
    reason VARCHAR(500) DEFAULT NULL,
    risk_check_status SMALLINT NOT NULL DEFAULT 1,
    risk_check_time TIMESTAMPTZ DEFAULT NULL,
    risk_rule_ids VARCHAR(200) DEFAULT NULL,
    source_order_no UUID DEFAULT NULL,
    created_by BIGINT NOT NULL,
    created_time TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_by BIGINT DEFAULT NULL,
    updated_time TIMESTAMPTZ DEFAULT NULL,
    is_deleted SMALLINT NOT NULL DEFAULT 0,
    remark VARCHAR(500) DEFAULT NULL,
    UNIQUE(order_no)
);

CREATE INDEX idx_trd_account ON trd_order(account_id);
CREATE INDEX idx_trd_symbol ON trd_order(symbol_id);
CREATE INDEX idx_trd_status ON trd_order(status);
CREATE INDEX idx_trd_created ON trd_order(created_time);
CREATE INDEX idx_trd_account_symbol ON trd_order(account_id, symbol_id, created_time);

-- ============================================
-- 15. 持仓表
-- ============================================
CREATE TABLE pos_position (
    id BIGSERIAL PRIMARY KEY,
    position_no UUID NOT NULL DEFAULT gen_random_uuid(),
    tenant_id BIGINT NOT NULL,
    account_id BIGINT NOT NULL,
    symbol_id BIGINT NOT NULL,
    position_side SMALLINT NOT NULL,
    open_lot DECIMAL(15,5) NOT NULL,
    current_lot DECIMAL(15,5) NOT NULL,
    open_price DECIMAL(20,10) NOT NULL,
    open_order_id BIGINT NOT NULL,
    close_lot DECIMAL(15,5) NOT NULL DEFAULT 0,
    close_price DECIMAL(20,10) DEFAULT NULL,
    realized_pnl DECIMAL(20,8) NOT NULL DEFAULT 0,
    unrealized_pnl DECIMAL(20,8) NOT NULL DEFAULT 0,
    swap_amount DECIMAL(20,8) NOT NULL DEFAULT 0,
    commission DECIMAL(20,8) NOT NULL DEFAULT 0,
    used_margin DECIMAL(20,8) NOT NULL DEFAULT 0,
    open_time TIMESTAMPTZ NOT NULL,
    update_time TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    is_locked SMALLINT NOT NULL DEFAULT 0,
    lock_reason VARCHAR(200) DEFAULT NULL,
    status SMALLINT NOT NULL DEFAULT 1,
    version INTEGER NOT NULL DEFAULT 1,
    created_by BIGINT NOT NULL,
    created_time TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_by BIGINT DEFAULT NULL,
    updated_time TIMESTAMPTZ DEFAULT NULL,
    is_deleted SMALLINT NOT NULL DEFAULT 0,
    remark VARCHAR(500) DEFAULT NULL,
    UNIQUE(position_no)
);

CREATE INDEX idx_pos_account ON pos_position(account_id);
CREATE INDEX idx_pos_status ON pos_position(status);

-- ============================================
-- 16. 风控规则表
-- ============================================
CREATE TABLE rsk_rule (
    id BIGSERIAL PRIMARY KEY,
    rule_code VARCHAR(50) NOT NULL,
    rule_name VARCHAR(100) NOT NULL,
    rule_type SMALLINT NOT NULL,
    symbol_id BIGINT DEFAULT NULL,
    account_group VARCHAR(50) DEFAULT NULL,
    risk_level SMALLINT DEFAULT NULL,
    min_value DECIMAL(20,8) DEFAULT NULL,
    max_value DECIMAL(20,8) DEFAULT NULL,
    action SMALLINT NOT NULL,
    action_level SMALLINT NOT NULL DEFAULT 1,
    priority INTEGER NOT NULL DEFAULT 100,
    status SMALLINT NOT NULL DEFAULT 1,
    effective_start TIMESTAMPTZ DEFAULT NULL,
    effective_end TIMESTAMPTZ DEFAULT NULL,
    rule_expression TEXT DEFAULT NULL,
    tenant_id BIGINT NOT NULL,
    created_by BIGINT NOT NULL,
    created_time TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_by BIGINT DEFAULT NULL,
    updated_time TIMESTAMPTZ DEFAULT NULL,
    is_deleted SMALLINT NOT NULL DEFAULT 0,
    remark VARCHAR(500) DEFAULT NULL,
    UNIQUE(rule_code, tenant_id)
);

-- ============================================
-- 17. 风控事件表
-- ============================================
CREATE TABLE rsk_event (
    id BIGSERIAL PRIMARY KEY,
    event_no UUID NOT NULL DEFAULT gen_random_uuid(),
    tenant_id BIGINT NOT NULL,
    rule_id BIGINT NOT NULL,
    rule_code VARCHAR(50) DEFAULT NULL,
    event_type SMALLINT NOT NULL,
    account_id BIGINT NOT NULL,
    symbol_id BIGINT DEFAULT NULL,
    order_no UUID DEFAULT NULL,
    position_no UUID DEFAULT NULL,
    event_data JSONB DEFAULT NULL,
    action_taken SMALLINT NOT NULL,
    operator_id BIGINT DEFAULT NULL,
    operate_time TIMESTAMPTZ DEFAULT NULL,
    operate_result VARCHAR(500) DEFAULT NULL,
    status SMALLINT NOT NULL DEFAULT 1,
    created_time TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_time TIMESTAMPTZ DEFAULT NULL,
    is_deleted SMALLINT NOT NULL DEFAULT 0,
    remark VARCHAR(500) DEFAULT NULL,
    UNIQUE(event_no)
);

CREATE INDEX idx_rsk_account ON rsk_event(account_id);
CREATE INDEX idx_rsk_status ON rsk_event(status);
CREATE INDEX idx_rsk_created ON rsk_event(created_time);

-- ============================================
-- 18. LP提供商表
-- ============================================
CREATE TABLE lp_provider (
    id BIGSERIAL PRIMARY KEY,
    provider_code VARCHAR(50) NOT NULL,
    provider_name VARCHAR(100) NOT NULL,
    provider_type SMALLINT NOT NULL,
    connection_type SMALLINT NOT NULL DEFAULT 1,
    api_endpoint VARCHAR(500) DEFAULT NULL,
    api_key VARCHAR(200) DEFAULT NULL,
    api_secret VARCHAR(500) DEFAULT NULL,
    supported_symbols TEXT DEFAULT NULL,
    priority INTEGER NOT NULL DEFAULT 100,
    status SMALLINT NOT NULL DEFAULT 1,
    min_lot DECIMAL(15,5) DEFAULT 0.01,
    max_lot DECIMAL(15,5) DEFAULT 100,
    timeout_ms INTEGER NOT NULL DEFAULT 5000,
    retry_count SMALLINT NOT NULL DEFAULT 3,
    tenant_id BIGINT NOT NULL,
    created_by BIGINT NOT NULL,
    created_time TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_by BIGINT DEFAULT NULL,
    updated_time TIMESTAMPTZ DEFAULT NULL,
    is_deleted SMALLINT NOT NULL DEFAULT 0,
    remark VARCHAR(500) DEFAULT NULL
);

-- ============================================
-- 19. 日结清表
-- ============================================
CREATE TABLE clr_daily_settlement (
    id BIGSERIAL PRIMARY KEY,
    settlement_no VARCHAR(30) NOT NULL,
    tenant_id BIGINT NOT NULL,
    account_id BIGINT NOT NULL,
    settlement_date DATE NOT NULL,
    open_equity DECIMAL(20,8) NOT NULL,
    close_equity DECIMAL(20,8) NOT NULL,
    realized_pnl DECIMAL(20,8) NOT NULL DEFAULT 0,
    unrealized_pnl DECIMAL(20,8) NOT NULL DEFAULT 0,
    swap_amount DECIMAL(20,8) NOT NULL DEFAULT 0,
    commission DECIMAL(20,8) NOT NULL DEFAULT 0,
    deposit DECIMAL(20,8) NOT NULL DEFAULT 0,
    withdrawal DECIMAL(20,8) NOT NULL DEFAULT 0,
    ib_commission DECIMAL(20,8) NOT NULL DEFAULT 0,
    balance_before DECIMAL(20,8) NOT NULL,
    balance_after DECIMAL(20,8) NOT NULL,
    margin_before DECIMAL(20,8) DEFAULT NULL,
    margin_after DECIMAL(20,8) DEFAULT NULL,
    status SMALLINT NOT NULL DEFAULT 1,
    error_message VARCHAR(500) DEFAULT NULL,
    processed_time TIMESTAMPTZ DEFAULT NULL,
    created_time TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    is_deleted SMALLINT NOT NULL DEFAULT 0,
    UNIQUE(settlement_no),
    UNIQUE(account_id, settlement_date)
);

-- ============================================
-- 20. KYC申请表
-- ============================================
CREATE TABLE kyc_application (
    id BIGSERIAL PRIMARY KEY,
    application_no UUID NOT NULL DEFAULT gen_random_uuid(),
    tenant_id BIGINT NOT NULL,
    customer_id BIGINT NOT NULL,
    kyc_level SMALLINT NOT NULL DEFAULT 1,
    kyc_provider VARCHAR(50) DEFAULT NULL,
    provider_ref_id VARCHAR(100) DEFAULT NULL,
    status SMALLINT NOT NULL DEFAULT 1,
    reject_reason VARCHAR(500) DEFAULT NULL,
    verify_result JSONB DEFAULT NULL,
    risk_score INTEGER DEFAULT 0,
    aml_check_result SMALLINT DEFAULT NULL,
    sanctions_hit SMALLINT DEFAULT 0,
    operator_id BIGINT DEFAULT NULL,
    operate_time TIMESTAMPTZ DEFAULT NULL,
    operate_remark VARCHAR(500) DEFAULT NULL,
    expire_time TIMESTAMPTZ DEFAULT NULL,
    created_by BIGINT NOT NULL,
    created_time TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_by BIGINT DEFAULT NULL,
    updated_time TIMESTAMPTZ DEFAULT NULL,
    is_deleted SMALLINT NOT NULL DEFAULT 0,
    remark VARCHAR(500) DEFAULT NULL
);

-- ============================================
-- 21. IB合伙人表
-- ============================================
CREATE TABLE ib_partner (
    id BIGSERIAL PRIMARY KEY,
    partner_no VARCHAR(30) NOT NULL,
    tenant_id BIGINT NOT NULL,
    customer_id BIGINT NOT NULL,
    partner_level SMALLINT NOT NULL DEFAULT 1,
    parent_id BIGINT DEFAULT NULL,
    hierarchy_path VARCHAR(500) DEFAULT NULL,
    commission_type SMALLINT NOT NULL DEFAULT 1,
    commission_config JSONB DEFAULT NULL,
    rebate_ratio DECIMAL(10,4) DEFAULT 0,
    status SMALLINT NOT NULL DEFAULT 1,
    total_customers INTEGER DEFAULT 0,
    active_customers INTEGER DEFAULT 0,
    total_commission DECIMAL(20,8) DEFAULT 0,
    pending_commission DECIMAL(20,8) DEFAULT 0,
    paid_commission DECIMAL(20,8) DEFAULT 0,
    brand_name VARCHAR(100) DEFAULT NULL,
    domain VARCHAR(100) DEFAULT NULL,
    logo_url VARCHAR(500) DEFAULT NULL,
    created_by BIGINT NOT NULL,
    created_time TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_by BIGINT DEFAULT NULL,
    updated_time TIMESTAMPTZ DEFAULT NULL,
    is_deleted SMALLINT NOT NULL DEFAULT 0,
    remark VARCHAR(500) DEFAULT NULL,
    UNIQUE(partner_no)
);

-- ============================================
-- 22. 工单表
-- ============================================
CREATE TABLE tic_ticket (
    id BIGSERIAL PRIMARY KEY,
    ticket_no VARCHAR(30) NOT NULL,
    tenant_id BIGINT NOT NULL,
    customer_id BIGINT NOT NULL,
    account_id BIGINT DEFAULT NULL,
    category SMALLINT NOT NULL,
    priority SMALLINT NOT NULL DEFAULT 2,
    title VARCHAR(200) NOT NULL,
    content TEXT NOT NULL,
    attachments JSONB DEFAULT NULL,
    related_order_no UUID DEFAULT NULL,
    status SMALLINT NOT NULL DEFAULT 1,
    assigned_to BIGINT DEFAULT NULL,
    assigned_time TIMESTAMPTZ DEFAULT NULL,
    first_reply_time TIMESTAMPTZ DEFAULT NULL,
    resolved_time TIMESTAMPTZ DEFAULT NULL,
    closed_time TIMESTAMPTZ DEFAULT NULL,
    satisfaction SMALLINT DEFAULT NULL,
    sla_deadline TIMESTAMPTZ DEFAULT NULL,
    sla_breached SMALLINT DEFAULT 0,
    created_by BIGINT NOT NULL,
    created_time TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_by BIGINT DEFAULT NULL,
    updated_time TIMESTAMPTZ DEFAULT NULL,
    is_deleted SMALLINT NOT NULL DEFAULT 0,
    remark VARCHAR(500) DEFAULT NULL,
    UNIQUE(ticket_no)
);

CREATE INDEX idx_tic_customer ON tic_ticket(customer_id);
CREATE INDEX idx_tic_assigned ON tic_ticket(assigned_to);
CREATE INDEX idx_tic_status ON tic_ticket(status);

-- ============================================
-- 23. 工单回复表
-- ============================================
CREATE TABLE tic_reply (
    id BIGSERIAL PRIMARY KEY,
    ticket_id BIGINT NOT NULL,
    tenant_id BIGINT NOT NULL,
    content TEXT NOT NULL,
    attachments JSONB DEFAULT NULL,
    reply_type SMALLINT NOT NULL DEFAULT 1,
    created_by BIGINT NOT NULL,
    created_time TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    is_deleted SMALLINT NOT NULL DEFAULT 0
);

-- ============================================
-- 24. 审计日志表
-- ============================================
CREATE TABLE aud_log (
    id BIGSERIAL PRIMARY KEY,
    log_no UUID NOT NULL DEFAULT gen_random_uuid(),
    tenant_id BIGINT NOT NULL,
    module VARCHAR(50) NOT NULL,
    action VARCHAR(100) NOT NULL,
    operator_type SMALLINT NOT NULL DEFAULT 1,
    operator_id BIGINT DEFAULT NULL,
    operator_name VARCHAR(100) DEFAULT NULL,
    ip_address INET DEFAULT NULL,
    user_agent VARCHAR(500) DEFAULT NULL,
    request_id UUID DEFAULT NULL,
    request_method VARCHAR(10) DEFAULT NULL,
    request_url VARCHAR(500) DEFAULT NULL,
    request_params JSONB DEFAULT NULL,
    request_body TEXT DEFAULT NULL,
    response_code VARCHAR(20) DEFAULT NULL,
    response_body TEXT DEFAULT NULL,
    execution_time INTEGER DEFAULT NULL,
    error_message TEXT DEFAULT NULL,
    status SMALLINT NOT NULL DEFAULT 1,
    extra_data JSONB DEFAULT NULL,
    created_time TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_aud_operator ON aud_log(operator_id);
CREATE INDEX idx_aud_module ON aud_log(module);
CREATE INDEX idx_aud_created ON aud_log(created_time);

-- ============================================
-- 25. 入金记录表
-- ============================================
CREATE TABLE txn_deposit (
    id BIGSERIAL PRIMARY KEY,
    deposit_no VARCHAR(30) NOT NULL,
    tenant_id BIGINT NOT NULL,
    account_id BIGINT NOT NULL,
    currency VARCHAR(10) NOT NULL,
    amount DECIMAL(20,8) NOT NULL,
    payment_method SMALLINT NOT NULL DEFAULT 1,
    payment_channel VARCHAR(50) DEFAULT NULL,
    reference_id VARCHAR(100) DEFAULT NULL,
    status SMALLINT NOT NULL DEFAULT 1,
    operator_id BIGINT DEFAULT NULL,
    operate_time TIMESTAMPTZ DEFAULT NULL,
    remark VARCHAR(500) DEFAULT NULL,
    created_by BIGINT NOT NULL,
    created_time TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_time TIMESTAMPTZ DEFAULT NULL,
    is_deleted SMALLINT NOT NULL DEFAULT 0,
    UNIQUE(deposit_no)
);

-- ============================================
-- 26. 出金记录表
-- ============================================
CREATE TABLE txn_withdrawal (
    id BIGSERIAL PRIMARY KEY,
    withdrawal_no VARCHAR(30) NOT NULL,
    tenant_id BIGINT NOT NULL,
    account_id BIGINT NOT NULL,
    currency VARCHAR(10) NOT NULL,
    amount DECIMAL(20,8) NOT NULL,
    bank_name VARCHAR(100) DEFAULT NULL,
    bank_account VARCHAR(100) DEFAULT NULL,
    bank_holder VARCHAR(100) DEFAULT NULL,
    status SMALLINT NOT NULL DEFAULT 1,
    operator_id BIGINT DEFAULT NULL,
    operate_time TIMESTAMPTZ DEFAULT NULL,
    reject_reason VARCHAR(500) DEFAULT NULL,
    remark VARCHAR(500) DEFAULT NULL,
    created_by BIGINT NOT NULL,
    created_time TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_time TIMESTAMPTZ DEFAULT NULL,
    is_deleted SMALLINT NOT NULL DEFAULT 0,
    UNIQUE(withdrawal_no)
);

-- ============================================
-- 27. 跟进记录表
-- ============================================
CREATE TABLE fll_follow (
    id BIGSERIAL PRIMARY KEY,
    tenant_id BIGINT NOT NULL,
    customer_id BIGINT NOT NULL,
    follow_type SMALLINT NOT NULL DEFAULT 1,
    content TEXT NOT NULL,
    next_follow_time TIMESTAMPTZ DEFAULT NULL,
    created_by BIGINT NOT NULL,
    created_time TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    is_deleted SMALLINT NOT NULL DEFAULT 0
);
