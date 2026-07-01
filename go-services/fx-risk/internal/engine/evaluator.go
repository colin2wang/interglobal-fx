package engine

import (
	"sort"
)

type Rule interface {
	GetID() string
	GetName() string
	GetPriority() int
	Evaluate(ctx *EvaluationContext) RuleResult
}

type Evaluator struct {
	rules []Rule
}

func NewEvaluator() *Evaluator {
	return &Evaluator{
		rules: make([]Rule, 0),
	}
}

func (e *Evaluator) RegisterRule(rule Rule) {
	e.rules = append(e.rules, rule)
}

func (e *Evaluator) Evaluate(ctx *EvaluationContext) *EvaluationContext {
	sort.Slice(e.rules, func(i, j int) bool {
		return e.rules[i].GetPriority() < e.rules[j].GetPriority()
	})
	for _, rule := range e.rules {
		if ctx.Stopped {
			break
		}
		result := rule.Evaluate(ctx)
		ctx.RuleResults = append(ctx.RuleResults, result)
		if !result.Passed {
			ctx.Stopped = true
			ctx.StopReason = result.Message
		}
	}
	return ctx
}
