package service

import (
	"fmt"

	"fx-mt-bridge/internal/config"
	"fx-mt-bridge/internal/protocol/mt4"
)

type MTService struct {
	manager      *mt4.Manager
	accountSync  *AccountSync
	orderSync    *OrderSync
	cfg          *config.Config
}

func NewMTService(cfg *config.Config) *MTService {
	conn := mt4.NewConnection(
		cfg.MT.Host,
		cfg.MT.Port,
		cfg.MT.Login,
		cfg.MT.Password,
		cfg.MT.TimeoutSeconds,
	)
	manager := mt4.NewManager(conn)
	return &MTService{
		manager:     manager,
		accountSync: NewAccountSync(manager, &cfg.Sync),
		orderSync:   NewOrderSync(manager, &cfg.Sync),
		cfg:         cfg,
	}
}

func (s *MTService) Connect() error {
	if err := s.manager.Connect(); err != nil {
		return fmt.Errorf("MT4 connect: %w", err)
	}
	go s.accountSync.Start()
	go s.orderSync.Start()
	return nil
}

func (s *MTService) Disconnect() error {
	s.accountSync.Stop()
	s.orderSync.Stop()
	return s.manager.Disconnect()
}

func (s *MTService) IsConnected() bool {
	return s.manager.IsConnected()
}

func (s *MTService) GetAccountInfo(login int64) (*mt4.AccountInfo, error) {
	if info, ok := s.accountSync.GetAccount(login); ok && info != nil {
		return info, nil
	}
	return s.manager.GetAccountInfo(login)
}

func (s *MTService) OpenOrder(login int64, symbol, orderType string, volume, price, sl, tp float64, comment string) (*mt4.OrderInfo, error) {
	return s.orderSync.ForwardOrder(login, symbol, orderType, volume, price, sl, tp, comment)
}

func (s *MTService) CloseOrder(orderID int64) error {
	return s.orderSync.CancelOrder(orderID)
}

func (s *MTService) GetOrders(login int64) []mt4.OrderInfo {
	return s.orderSync.GetOrders(login)
}

func (s *MTService) GetPositions(login int64) ([]mt4.PositionInfo, error) {
	return s.manager.GetPositions(login)
}
