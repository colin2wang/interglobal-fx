package engine

import "fx-risk/internal/model"

type EvaluationContext struct {
	Request       *model.RiskCheckRequest
	AccountState  *AccountState
	RuleResults   []RuleResult
	Stopped       bool
	StopReason    string
}

type AccountState struct {
	AccountID      string
	TotalPosition  float64
	OpenOrders     int
	MarginUsed     float64
	AvailableBalance float64
	RecentTrades   []TradeRecord
}

type TradeRecord struct {
	Timestamp int64
	Quantity  float64
	Symbol    string
}

type RuleResult struct {
	RuleID  string
	Passed  bool
	Message string
}

func NewEvaluationContext(req *model.RiskCheckRequest) *EvaluationContext {
	return &EvaluationContext{
		Request:      req,
		AccountState: &AccountState{},
		RuleResults:  make([]RuleResult, 0),
	}
}
