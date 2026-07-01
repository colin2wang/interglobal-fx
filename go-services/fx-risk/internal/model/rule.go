package model

type RiskRule struct {
	ID       string `json:"id"`
	Name     string `json:"name"`
	Type     string `json:"type"`
	Enabled  bool   `json:"enabled"`
	Priority int    `json:"priority"`
	Config   map[string]interface{} `json:"config"`
}

type RiskCheckRequest struct {
	AccountID  string  `json:"account_id"`
	Symbol     string  `json:"symbol"`
	Side       string  `json:"side"`
	OrderType  string  `json:"order_type"`
	Quantity   float64 `json:"quantity"`
	Price      float64 `json:"price"`
	Leverage   float64 `json:"leverage"`
}

type RiskCheckResponse struct {
	Code    int      `json:"code"`
	Allowed bool     `json:"allowed"`
	Reason  string   `json:"reason,omitempty"`
	Rules   []string `json:"rules_checked,omitempty"`
}
