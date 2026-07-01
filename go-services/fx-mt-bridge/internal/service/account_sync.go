package service

import (
	"sync"
	"time"

	"fx-mt-bridge/internal/config"
	"fx-mt-bridge/internal/protocol/mt4"
)

type AccountSync struct {
	manager    *mt4.Manager
	cfg        *config.SyncConfig
	accounts   map[int64]*mt4.AccountInfo
	mu         sync.RWMutex
	stopCh     chan struct{}
}

func NewAccountSync(manager *mt4.Manager, cfg *config.SyncConfig) *AccountSync {
	return &AccountSync{
		manager:  manager,
		cfg:      cfg,
		accounts: make(map[int64]*mt4.AccountInfo),
		stopCh:   make(chan struct{}),
	}
}

func (s *AccountSync) Start() {
	interval := time.Duration(s.cfg.AccountIntervalSecs) * time.Second
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

func (s *AccountSync) Stop() {
	close(s.stopCh)
}

func (s *AccountSync) syncAll() {
	s.mu.RLock()
	logins := make([]int64, 0, len(s.accounts))
	for login := range s.accounts {
		logins = append(logins, login)
	}
	s.mu.RUnlock()
	for _, login := range logins {
		info, err := s.manager.GetAccountInfo(login)
		if err != nil {
			continue
		}
		s.mu.Lock()
		s.accounts[login] = info
		s.mu.Unlock()
	}
}

func (s *AccountSync) GetAccount(login int64) (*mt4.AccountInfo, bool) {
	s.mu.RLock()
	defer s.mu.RUnlock()
	info, ok := s.accounts[login]
	return info, ok
}

func (s *AccountSync) RegisterAccount(login int64) {
	s.mu.Lock()
	defer s.mu.Unlock()
	s.accounts[login] = nil
}
