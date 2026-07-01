-- GlobalFX Trading Platform - Initial Data

-- Default tenant
INSERT INTO cfg_tenant (tenant_code, tenant_name, tenant_type, default_leverage, default_currency, status, created_by) VALUES
('GLOBALFX', '环球汇通', 1, 30, 'USD', 1, 1);

-- Default admin user (password: Admin@123456)
INSERT INTO usr_user (user_no, tenant_id, username, password, nickname, email, status, created_by) VALUES
('SYS000001', 1, 'admin', '$2a$12$LJ3m4ys3Lg.KfQ.RxqYq/.YxYRqY8vqY8vqY8vqY8vqY8vqY8vq', '超级管理员', 'admin@globalfx.com', 1, 1);

-- Default roles
INSERT INTO usr_role (role_code, role_name, tenant_id, data_scope, status, created_by) VALUES
('super_admin', '超级管理员', 1, 1, 1, 1),
('admin', '管理员', 1, 1, 1, 1),
('risk_manager', '风控主管', 1, 2, 1, 1),
('risk_operator', '风控专员', 1, 2, 1, 1),
('ops_manager', '运营主管', 1, 2, 1, 1),
('operator', '运营专员', 1, 2, 1, 1),
('compliance_manager', '合规主管', 1, 2, 1, 1),
('customer_manager', '客户经理', 1, 4, 1, 1),
('trader', '交易员', 1, 4, 1, 1);

-- Assign super_admin role to admin user
INSERT INTO usr_user_role (user_id, role_id, tenant_id, created_by) VALUES (1, 1, 1, 1);

-- Dictionary types
INSERT INTO dic_type (dict_type, dict_name, tenant_id, status, created_by) VALUES
('order_type', '订单类型', 0, 1, 1),
('order_side', '订单方向', 0, 1, 1),
('order_status', '订单状态', 0, 1, 1),
('position_status', '持仓状态', 0, 1, 1),
('account_type', '账户类型', 0, 1, 1),
('account_status', '账户状态', 0, 1, 1),
('risk_level', '客户风险等级', 0, 1, 1),
('kyc_status', 'KYC状态', 0, 1, 1),
('txn_type', '资金流水类型', 0, 1, 1),
('symbol_type', '品种类型', 0, 1, 1),
('risk_action', '风控动作', 0, 1, 1),
('risk_event_type', '风控事件类型', 0, 1, 1),
('ticket_category', '工单类别', 0, 1, 1),
('ticket_status', '工单状态', 0, 1, 1),
('customer_status', '客户状态', 0, 1, 1),
('user_status', '用户状态', 0, 1, 1),
('channel', '订单来源', 0, 1, 1),
('execution_mode', '执行模式', 0, 1, 1),
('trade_mode', '交易模式', 0, 1, 1),
('yes_no', '是否', 0, 1, 1);

-- Order type data
INSERT INTO dic_data (dict_type, dict_label, dict_value, sort_order, status, tenant_id, created_by) VALUES
('order_type', '市价单', '1', 1, 1, 0, 1), ('order_type', '限价单', '2', 2, 1, 0, 1),
('order_type', '止损单', '3', 3, 1, 0, 1), ('order_type', '止盈单', '4', 4, 1, 0, 1),
('order_type', '止损限价单', '5', 5, 1, 0, 1), ('order_type', '挂单', '6', 6, 1, 0, 1);

-- Order side data
INSERT INTO dic_data (dict_type, dict_label, dict_value, sort_order, status, tenant_id, created_by) VALUES
('order_side', '买入', '1', 1, 1, 0, 1), ('order_side', '卖出', '2', 2, 1, 0, 1);

-- Order status data
INSERT INTO dic_data (dict_type, dict_label, dict_value, sort_order, status, tenant_id, created_by) VALUES
('order_status', '挂单中', '1', 1, 1, 0, 1), ('order_status', '部分成交', '2', 2, 1, 0, 1),
('order_status', '完全成交', '3', 3, 1, 0, 1), ('order_status', '已取消', '4', 4, 1, 0, 1),
('order_status', '已拒绝', '5', 5, 1, 0, 1), ('order_status', '已过期', '6', 6, 1, 0, 1);

-- Position status data
INSERT INTO dic_data (dict_type, dict_label, dict_value, sort_order, status, tenant_id, created_by) VALUES
('position_status', '持仓中', '1', 1, 1, 0, 1), ('position_status', '已平仓', '2', 2, 1, 0, 1),
('position_status', '部分平仓', '3', 3, 1, 0, 1), ('position_status', '强平中', '4', 4, 1, 0, 1);

-- Account type data
INSERT INTO dic_data (dict_type, dict_label, dict_value, sort_order, status, tenant_id, created_by) VALUES
('account_type', '真实账户', '1', 1, 1, 0, 1), ('account_type', '模拟账户', '2', 2, 1, 0, 1),
('account_type', 'VIP账户', '3', 3, 1, 0, 1);

-- Account status data
INSERT INTO dic_data (dict_type, dict_label, dict_value, sort_order, status, tenant_id, created_by) VALUES
('account_status', '正常', '1', 1, 1, 0, 1), ('account_status', '冻结', '2', 2, 1, 0, 1),
('account_status', '关闭', '3', 3, 1, 0, 1), ('account_status', '强平中', '4', 4, 1, 0, 1);

-- Risk level data
INSERT INTO dic_data (dict_type, dict_label, dict_value, sort_order, status, tenant_id, created_by) VALUES
('risk_level', '零售客户', '1', 1, 1, 0, 1), ('risk_level', '专业客户', '2', 2, 1, 0, 1),
('risk_level', '机构客户', '3', 3, 1, 0, 1);

-- KYC status data
INSERT INTO dic_data (dict_type, dict_label, dict_value, sort_order, status, tenant_id, created_by) VALUES
('kyc_status', '未提交', '0', 0, 1, 0, 1), ('kyc_status', '待审核', '1', 1, 1, 0, 1),
('kyc_status', '审核中', '2', 2, 1, 0, 1), ('kyc_status', '已通过', '3', 3, 1, 0, 1),
('kyc_status', '已拒绝', '4', 4, 1, 0, 1);

-- Symbol type data
INSERT INTO dic_data (dict_type, dict_label, dict_value, sort_order, status, tenant_id, created_by) VALUES
('symbol_type', '主要货币对', '1', 1, 1, 0, 1), ('symbol_type', '次要货币对', '2', 2, 1, 0, 1),
('symbol_type', 'Exotic货币对', '3', 3, 1, 0, 1), ('symbol_type', '贵金属', '4', 4, 1, 0, 1),
('symbol_type', '指数', '5', 5, 1, 0, 1), ('symbol_type', '加密货币', '6', 6, 1, 0, 1);

-- Risk action data
INSERT INTO dic_data (dict_type, dict_label, dict_value, sort_order, status, tenant_id, created_by) VALUES
('risk_action', '拒绝', '1', 1, 1, 0, 1), ('risk_action', '警告', '2', 2, 1, 0, 1),
('risk_action', '延迟', '3', 3, 1, 0, 1), ('risk_action', '人工审核', '4', 4, 1, 0, 1);

-- Risk event type data
INSERT INTO dic_data (dict_type, dict_label, dict_value, sort_order, status, tenant_id, created_by) VALUES
('risk_event_type', '订单拒绝', '1', 1, 1, 0, 1), ('risk_event_type', '风控警告', '2', 2, 1, 0, 1),
('risk_event_type', '强制平仓', '3', 3, 1, 0, 1), ('risk_event_type', '账户冻结', '4', 4, 1, 0, 1),
('risk_event_type', '异常检测', '5', 5, 1, 0, 1);

-- Ticket category data
INSERT INTO dic_data (dict_type, dict_label, dict_value, sort_order, status, tenant_id, created_by) VALUES
('ticket_category', '交易问题', '1', 1, 1, 0, 1), ('ticket_category', '出金问题', '2', 2, 1, 0, 1),
('ticket_category', '账户问题', '3', 3, 1, 0, 1), ('ticket_category', '系统问题', '4', 4, 1, 0, 1),
('ticket_category', '投诉', '5', 5, 1, 0, 1), ('ticket_category', '建议', '6', 6, 1, 0, 1),
('ticket_category', '其他', '7', 7, 1, 0, 1);

-- Ticket status data
INSERT INTO dic_data (dict_type, dict_label, dict_value, sort_order, status, tenant_id, created_by) VALUES
('ticket_status', '待分配', '1', 1, 1, 0, 1), ('ticket_status', '处理中', '2', 2, 1, 0, 1),
('ticket_status', '待回复', '3', 3, 1, 0, 1), ('ticket_status', '已解决', '4', 4, 1, 0, 1),
('ticket_status', '已关闭', '5', 5, 1, 0, 1);

-- Customer status data
INSERT INTO dic_data (dict_type, dict_label, dict_value, sort_order, status, tenant_id, created_by) VALUES
('customer_status', '潜在', '1', 1, 1, 0, 1), ('customer_status', '活跃', '2', 2, 1, 0, 1),
('customer_status', '沉默', '3', 3, 1, 0, 1), ('customer_status', '流失', '4', 4, 1, 0, 1);

-- User status data
INSERT INTO dic_data (dict_type, dict_label, dict_value, sort_order, status, tenant_id, created_by) VALUES
('user_status', '正常', '1', 1, 1, 0, 1), ('user_status', '停用', '2', 2, 1, 0, 1),
('user_status', '锁定', '3', 3, 1, 0, 1);

-- Channel data
INSERT INTO dic_data (dict_type, dict_label, dict_value, sort_order, status, tenant_id, created_by) VALUES
('channel', 'Web端', '1', 1, 1, 0, 1), ('channel', 'App端', '2', 2, 1, 0, 1),
('channel', 'API接口', '3', 3, 1, 0, 1), ('channel', 'MT4', '4', 4, 1, 0, 1),
('channel', 'MT5', '5', 5, 1, 0, 1), ('channel', 'FIX协议', '6', 6, 1, 0, 1);

-- Execution mode data
INSERT INTO dic_data (dict_type, dict_label, dict_value, sort_order, status, tenant_id, created_by) VALUES
('execution_mode', 'A-Book', '1', 1, 1, 0, 1), ('execution_mode', 'B-Book', '2', 2, 1, 0, 1),
('execution_mode', 'Hybrid', '3', 3, 1, 0, 1);

-- Trade mode data
INSERT INTO dic_data (dict_type, dict_label, dict_value, sort_order, status, tenant_id, created_by) VALUES
('trade_mode', '可交易', '1', 1, 1, 0, 1), ('trade_mode', '仅查看', '2', 2, 1, 0, 1),
('trade_mode', '停用', '3', 3, 1, 0, 1);

-- Yes/No data
INSERT INTO dic_data (dict_type, dict_label, dict_value, sort_order, status, tenant_id, created_by) VALUES
('yes_no', '否', '0', 0, 1, 0, 1), ('yes_no', '是', '1', 1, 1, 0, 1);

-- Txn type data
INSERT INTO dic_data (dict_type, dict_label, dict_value, sort_order, status, tenant_id, created_by) VALUES
('txn_type', '入金', '1', 1, 1, 0, 1), ('txn_type', '出金', '2', 2, 1, 0, 1),
('txn_type', '转账', '3', 3, 1, 0, 1), ('txn_type', '交易', '4', 4, 1, 0, 1),
('txn_type', '清算', '5', 5, 1, 0, 1), ('txn_type', '佣金', '6', 6, 1, 0, 1),
('txn_type', '利息', '7', 7, 1, 0, 1), ('txn_type', '手续费', '8', 8, 1, 0, 1),
('txn_type', '其他', '9', 9, 1, 0, 1);

-- Default symbols
INSERT INTO sym_symbol (symbol, symbol_name, symbol_type, base_currency, quote_currency, precision, lot_size, min_lot, max_lot, lot_step, min_distance_points, trade_mode, contract_size, swap_long, swap_short, trading_hours, status, tenant_id, created_by) VALUES
('EURUSD', '欧元/美元', 1, 'EUR', 'USD', 5, 100000, 0.01, 100, 0.01, 10, 1, 100000, -0.5, 0.3, '00:00-24:00', 1, 1, 1),
('GBPUSD', '英镑/美元', 1, 'GBP', 'USD', 5, 100000, 0.01, 100, 0.01, 10, 1, 100000, -0.8, 0.2, '00:00-24:00', 1, 1, 1),
('USDJPY', '美元/日元', 1, 'USD', 'JPY', 3, 100000, 0.01, 100, 0.01, 10, 1, 100000, 0.5, -1.2, '00:00-24:00', 1, 1, 1),
('USDCHF', '美元/瑞郎', 1, 'USD', 'CHF', 5, 100000, 0.01, 100, 0.01, 10, 1, 100000, 0.3, -0.6, '00:00-24:00', 1, 1, 1),
('AUDUSD', '澳元/美元', 1, 'AUD', 'USD', 5, 100000, 0.01, 100, 0.01, 10, 1, 100000, -0.3, 0.1, '00:00-24:00', 1, 1, 1),
('USDCAD', '美元/加元', 1, 'USD', 'CAD', 5, 100000, 0.01, 100, 0.01, 10, 1, 100000, -0.4, 0.2, '00:00-24:00', 1, 1, 1),
('NZDUSD', '纽元/美元', 1, 'NZD', 'USD', 5, 100000, 0.01, 100, 0.01, 10, 1, 100000, -0.2, 0.1, '00:00-24:00', 1, 1, 1),
('XAUUSD', '黄金/美元', 4, 'XAU', 'USD', 2, 100, 0.01, 100, 0.01, 30, 1, 100, -2.0, 1.5, '00:00-24:00', 1, 1, 1),
('BTCUSD', '比特币/美元', 6, 'BTC', 'USD', 2, 1, 0.001, 10, 0.001, 100, 1, 1, -5.0, 2.0, '00:00-24:00', 1, 1, 1),
('EURGBP', '欧元/英镑', 2, 'EUR', 'GBP', 5, 100000, 0.01, 100, 0.01, 10, 1, 100000, -0.3, 0.1, '00:00-24:00', 1, 1, 1),
('EURJPY', '欧元/日元', 2, 'EUR', 'JPY', 3, 100000, 0.01, 100, 0.01, 10, 1, 100000, 0.2, -0.8, '00:00-24:00', 1, 1, 1),
('GBPJPY', '英镑/日元', 2, 'GBP', 'JPY', 3, 100000, 0.01, 100, 0.01, 15, 1, 100000, 0.3, -1.0, '00:00-24:00', 1, 1, 1);
