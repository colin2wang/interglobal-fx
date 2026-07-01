package service

import (
	"fx-risk/internal/config"
	"fx-risk/internal/engine"
	"fx-risk/internal/engine/rules"
	"fx-risk/internal/model"
)

type RuleEngine struct {
	evaluator *engine.Evaluator
}

func NewRuleEngine(cfg *config.RiskConfig) *RuleEngine {
	evaluator := engine.NewEvaluator()
	evaluator.RegisterRule(rules.NewPositionRule(cfg.MaxPositionSize))
	evaluator.RegisterRule(rules.NewOrderRule(cfg.MaxOrderSize, cfg.MaxOpenOrders))
	evaluator.RegisterRule(rules.NewMarginRule(cfg.MaxLeverage, cfg.MinMarginRatio))
	return &RuleEngine{evaluator: evaluator}
}

func (re *RuleEngine) Evaluate(req *model.RiskCheckRequest, state *engine.AccountState) *engine.EvaluationContext {
	ctx := engine.NewEvaluationContext(req)
	ctx.AccountState = state
	return re.evaluator.Evaluate(ctx)
}
