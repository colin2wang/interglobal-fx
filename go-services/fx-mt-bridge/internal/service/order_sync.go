package service

import (
	"sync"
	"time"

	"fx-mt-bridge/internal/config"
	"fx-mt-bridge/internal/protocol/mt4"
)

type OrderSync struct {
	manager  *mt4.Manager
	cfg      *config.SyncConfig
	orders   map[int64][]mt4.OrderInfo
	mu       sync.RWMutex
	stopCh   chan struct{}
}

func NewOrderSync(manager *mt4.Manager, cfg *config.SyncConfig) *OrderSync {
	return &OrderSync{
		manager: manager,
		cfg:     cfg,
		orders:  make(map[int64][]mt4.OrderInfo),
		stopCh:  make(chan struct{}),
	}
}

func (s *OrderSync) Start() {
	interval := time.Duration(s.cfg.OrderIntervalSecs) * time.Second
	ticker := time.NewTicker(interval)
	defer ticker.Stop()
	for {
		select {
		case <-s.stopCh:
			return
		case <-ticker.C:
			s.syncAll()
		}
	}
}

func (s *OrderSync) Stop() {
	close(s.stopCh)
}

func (s *OrderSync) syncAll() {
	s.mu.RLock()
	logins := make([]int64, 0, len(s.orders))
	for login := range s.orders {
		logins = append(logins, login)
	}
	s.mu.RUnlock()
	for _, login := range logins {
		orders, err := s.manager.GetOrders(login)
		if err != nil {
			continue
		}
		s.mu.Lock()
		s.orders[login] = orders
		s.mu.Unlock()
	}
}

func (s *OrderSync) GetOrders(login int64) []mt4.OrderInfo {
	s.mu.RLock()
	defer s.mu.RUnlock()
	return s.orders[login]
}

func (s *OrderSync) ForwardOrder(login int64, symbol, orderType string, volume, price, sl, tp float64, comment string) (*mt4.OrderInfo, error) {
	return s.manager.OpenOrder(login, symbol, orderType, volume, price, sl, tp, comment)
}

func (s *OrderSync) CancelOrder(orderID int64) error {
	return s.manager.CloseOrder(orderID)
}
