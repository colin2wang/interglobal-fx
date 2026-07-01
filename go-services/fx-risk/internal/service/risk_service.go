package service

import (
	"fmt"
	"time"

	"fx-risk/internal/config"
	"fx-risk/internal/engine"
	"fx-risk/internal/model"
	"fx-risk/internal/repository"
)

type RiskService struct {
	riskRepo   *repository.RiskRepository
	ruleEngine *RuleEngine
	anomaly    *AnomalyDetector
	cfg        *config.Config
}

func NewRiskService(cfg *config.Config) *RiskService {
	return &RiskService{
		riskRepo:   repository.NewRiskRepository(),
		ruleEngine: NewRuleEngine(&cfg.Risk),
		anomaly:    NewAnomalyDetector(&cfg.Risk),
		cfg:        cfg,
	}
}

func (s *RiskService) CheckRisk(req *model.RiskCheckRequest) *model.RiskCheckResponse {
	state := &engine.AccountState{
		AccountID:        req.AccountID,
		TotalPosition:    0,
		OpenOrders:       0,
		MarginUsed:       0,
		AvailableBalance: 100000,
	}
	ctx := s.ruleEngine.Evaluate(req, state)

	if ctx.Stopped {
		s.recordEvent(req, "rule_violation", ctx.StopReason)
		return &model.RiskCheckResponse{
			Code:    0,
			Allowed: false,
			Reason:  ctx.StopReason,
			Rules:   s.extractRuleIDs(ctx),
		}
	}

	if freq, msg := s.anomaly.CheckFrequency(req.AccountID); freq {
		s.recordEvent(req, "anomaly_frequency", msg)
		return &model.RiskCheckResponse{
			Code:    0,
			Allowed: false,
			Reason:  msg,
			Rules:   []string{"anomaly_detector"},
		}
	}

	s.anomaly.RecordTrade(req.AccountID, req)
	return &model.RiskCheckResponse{
		Code:    0,
		Allowed: true,
		Rules:   s.extractRuleIDs(ctx),
	}
}

func (s *RiskService) recordEvent(req *model.RiskCheckRequest, eventType, detail string) {
	event := &model.RiskEvent{
		AccountID: req.AccountID,
		EventType: eventType,
		Symbol:    req.Symbol,
		Detail:    detail,
		Amount:    req.Quantity * req.Price,
		Timestamp: time.Now().UnixMilli(),
	}
	_ = s.riskRepo.SaveEvent(event)
}

func (s *RiskService) extractRuleIDs(ctx *engine.EvaluationContext) []string {
	var ids []string
	for _, r := range ctx.RuleResults {
		ids = append(ids, r.RuleID)
	}
	return ids
}

func (s *RiskService) GetRiskEvents(accountID string, limit int) ([]*model.RiskEvent, error) {
	if accountID == "" {
		return s.riskRepo.GetAllEvents(limit)
	}
	events, err := s.riskRepo.GetEventsByAccount(accountID, limit)
	if err != nil {
		return nil, fmt.Errorf("get events: %w", err)
	}
	return events, nil
}
