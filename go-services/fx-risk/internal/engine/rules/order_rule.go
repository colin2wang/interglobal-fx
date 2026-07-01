package rules

import (
	"fmt"

	"fx-risk/internal/engine"
)

type OrderRule struct {
	ID           string
	Name         string
	Priority     int
	MaxOrderSize float64
	MaxOpenOrders int
}

func NewOrderRule(maxSize float64, maxOpen int) *OrderRule {
	return &OrderRule{
		ID:            "order_limit",
		Name:          "Order Limit Rule",
		Priority:      2,
		MaxOrderSize:  maxSize,
		MaxOpenOrders: maxOpen,
	}
}

func (r *OrderRule) GetID() string       { return r.ID }
func (r *OrderRule) GetName() string     { return r.Name }
func (r *OrderRule) GetPriority() int    { return r.Priority }

func (r *OrderRule) Evaluate(ctx *engine.EvaluationContext) engine.RuleResult {
	req := ctx.Request
	if req.Quantity > r.MaxOrderSize {
		return engine.RuleResult{
			RuleID:  r.ID,
			Passed:  false,
			Message: fmt.Sprintf("order size %.2f exceeds max %.2f", req.Quantity, r.MaxOrderSize),
		}
	}
	if ctx.AccountState.OpenOrders >= r.MaxOpenOrders {
		return engine.RuleResult{
			RuleID:  r.ID,
			Passed:  false,
			Message: fmt.Sprintf("open orders %d exceeds max %d", ctx.AccountState.OpenOrders, r.MaxOpenOrders),
		}
	}
	return engine.RuleResult{
		RuleID:  r.ID,
		Passed:  true,
		Message: "order limit check passed",
	}
}
