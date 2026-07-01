package rules

import (
	"fmt"

	"fx-risk/internal/engine"
)

type PositionRule struct {
	ID              string
	Name            string
	Priority        int
	MaxPositionSize float64
}

func NewPositionRule(maxSize float64) *PositionRule {
	return &PositionRule{
		ID:              "position_limit",
		Name:            "Position Limit Rule",
		Priority:        1,
		MaxPositionSize: maxSize,
	}
}

func (r *PositionRule) GetID() string       { return r.ID }
func (r *PositionRule) GetName() string     { return r.Name }
func (r *PositionRule) GetPriority() int    { return r.Priority }

func (r *PositionRule) Evaluate(ctx *engine.EvaluationContext) engine.RuleResult {
	req := ctx.Request
	newPosition := ctx.AccountState.TotalPosition
	if req.Side == "buy" {
		newPosition += req.Quantity
	} else {
		newPosition -= req.Quantity
	}
	if newPosition > r.MaxPositionSize {
		return engine.RuleResult{
			RuleID:  r.ID,
			Passed:  false,
			Message: fmt.Sprintf("position size %.2f exceeds max %.2f", newPosition, r.MaxPositionSize),
		}
	}
	return engine.RuleResult{
		RuleID:  r.ID,
		Passed:  true,
		Message: "position limit check passed",
	}
}
