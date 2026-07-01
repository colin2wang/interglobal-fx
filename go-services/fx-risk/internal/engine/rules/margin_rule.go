package rules

import (
	"fmt"

	"fx-risk/internal/engine"
)

type MarginRule struct {
	ID             string
	Name           string
	Priority       int
	MaxLeverage    float64
	MinMarginRatio float64
}

func NewMarginRule(maxLeverage, minMarginRatio float64) *MarginRule {
	return &MarginRule{
		ID:             "margin_check",
		Name:           "Margin Rule",
		Priority:       3,
		MaxLeverage:    maxLeverage,
		MinMarginRatio: minMarginRatio,
	}
}

func (r *MarginRule) GetID() string       { return r.ID }
func (r *MarginRule) GetName() string     { return r.Name }
func (r *MarginRule) GetPriority() int    { return r.Priority }

func (r *MarginRule) Evaluate(ctx *engine.EvaluationContext) engine.RuleResult {
	req := ctx.Request
	if req.Leverage > r.MaxLeverage {
		return engine.RuleResult{
			RuleID:  r.ID,
			Passed:  false,
			Message: fmt.Sprintf("leverage %.2f exceeds max %.2f", req.Leverage, r.MaxLeverage),
		}
	}
	orderValue := req.Quantity * req.Price
	requiredMargin := orderValue / req.Leverage
	if req.Leverage > 0 {
		marginRatio := ctx.AccountState.MarginUsed / ctx.AccountState.AvailableBalance
		if marginRatio > r.MinMarginRatio {
			return engine.RuleResult{
				RuleID:  r.ID,
				Passed:  false,
				Message: fmt.Sprintf("margin ratio %.4f exceeds min %.4f", marginRatio, r.MinMarginRatio),
			}
		}
		_ = requiredMargin
	}
	return engine.RuleResult{
		RuleID:  r.ID,
		Passed:  true,
		Message: "margin check passed",
	}
}
