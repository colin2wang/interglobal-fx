package repository

import (
	"fmt"
	"sync"
	"time"

	"fx-risk/internal/model"
)

type RiskRepository struct {
	events []*model.RiskEvent
	mu     sync.RWMutex
	nextID int
}

func NewRiskRepository() *RiskRepository {
	return &RiskRepository{
		events: make([]*model.RiskEvent, 0),
		nextID: 1,
	}
}

func (r *RiskRepository) SaveEvent(event *model.RiskEvent) error {
	r.mu.Lock()
	defer r.mu.Unlock()
	event.ID = fmt.Sprintf("evt_%d", r.nextID)
	r.nextID++
	if event.Timestamp == 0 {
		event.Timestamp = time.Now().UnixMilli()
	}
	r.events = append(r.events, event)
	return nil
}

func (r *RiskRepository) GetEventsByAccount(accountID string, limit int) ([]*model.RiskEvent, error) {
	r.mu.RLock()
	defer r.mu.RUnlock()
	var result []*model.RiskEvent
	for i := len(r.events) - 1; i >= 0 && len(result) < limit; i-- {
		if r.events[i].AccountID == accountID {
			result = append(result, r.events[i])
		}
	}
	return result, nil
}

func (r *RiskRepository) GetEventsByType(eventType string, limit int) ([]*model.RiskEvent, error) {
	r.mu.RLock()
	defer r.mu.RUnlock()
	var result []*model.RiskEvent
	for i := len(r.events) - 1; i >= 0 && len(result) < limit; i-- {
		if r.events[i].EventType == eventType {
			result = append(result, r.events[i])
		}
	}
	return result, nil
}

func (r *RiskRepository) GetAllEvents(limit int) ([]*model.RiskEvent, error) {
	r.mu.RLock()
	defer r.mu.RUnlock()
	start := len(r.events) - limit
	if start < 0 {
		start = 0
	}
	result := make([]*model.RiskEvent, len(r.events[start:]))
	copy(result, r.events[start:])
	return result, nil
}
