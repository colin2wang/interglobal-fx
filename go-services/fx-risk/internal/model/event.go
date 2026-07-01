package model

type RiskEvent struct {
	ID        string  `json:"id"`
	AccountID string  `json:"account_id"`
	EventType string  `json:"event_type"`
	RuleID    string  `json:"rule_id"`
	Symbol    string  `json:"symbol"`
	Detail    string  `json:"detail"`
	Amount    float64 `json:"amount"`
	Timestamp int64   `json:"timestamp"`
}

type RiskEventResponse struct {
	Code int        `json:"code"`
	Msg  string     `json:"msg"`
	Data *RiskEvent `json:"data,omitempty"`
}

type RiskEventListResponse struct {
	Code int          `json:"code"`
	Msg  string       `json:"msg"`
	Data []*RiskEvent `json:"data,omitempty"`
}
