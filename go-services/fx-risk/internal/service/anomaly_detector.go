package service

import (
	"sync"
	"time"

	"fx-risk/internal/config"
	"fx-risk/internal/model"
)

type AnomalyDetector struct {
	cfg     *config.RiskConfig
	records map[string][]time.Time
	mu      sync.Mutex
}

func NewAnomalyDetector(cfg *config.RiskConfig) *AnomalyDetector {
	return &AnomalyDetector{
		cfg:     cfg,
		records: make(map[string][]time.Time),
	}
}

func (d *AnomalyDetector) CheckFrequency(accountID string) (bool, string) {
	d.mu.Lock()
	defer d.mu.Unlock()
	now := time.Now()
	window := time.Duration(d.cfg.FrequencyWindowSecs) * time.Second
	cutoff := now.Add(-window)
	key := accountID
	records := d.records[key]
	var filtered []time.Time
	for _, t := range records {
		if t.After(cutoff) {
			filtered = append(filtered, t)
		}
	}
	filtered = append(filtered, now)
	d.records[key] = filtered
	if len(filtered) > d.cfg.FrequencyMaxTrades {
		return true, "frequency trading detected: too many trades in window"
	}
	return false, ""
}

func (d *AnomalyDetector) CheckWashTrading(accountID string, symbol string, side string, _ float64) (bool, string) {
	d.mu.Lock()
	defer d.mu.Unlock()
	key := accountID + ":" + symbol
	now := time.Now()
	window := 5 * time.Minute
	_ = window
	_ = now
	_ = key
	_ = side
	return false, ""
}

func (d *AnomalyDetector) RecordTrade(accountID string, tick *model.RiskCheckRequest) {
	d.mu.Lock()
	defer d.mu.Unlock()
	key := accountID
	d.records[key] = append(d.records[key], time.Now())
}
