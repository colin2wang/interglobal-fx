package service

import (
	"fmt"
	"net"
	"sync"
	"time"

	"fx-fix-gateway/internal/config"
	"fx-fix-gateway/internal/protocol/fix"
	"fx-fix-gateway/pkg/logger"
)

type FixService struct {
	parser        *fix.Parser
	sessionMgr    *fix.SessionManager
	sessionSvc    *SessionManagerService
	cfg           *config.Config
	ln            net.Listener
	mu            sync.RWMutex
	stopCh        chan struct{}
}

func NewFixService(cfg *config.Config) *FixService {
	sessionSvc := NewSessionManagerService(cfg)
	return &FixService{
		parser:     fix.NewParser(),
		sessionMgr: fix.NewSessionManager(),
		sessionSvc: sessionSvc,
		cfg:        cfg,
		stopCh:     make(chan struct{}),
	}
}

func (s *FixService) Start() error {
	addr := s.cfg.FIX.ListenAddr
	if addr == "" {
		addr = ":9876"
	}
	ln, err := net.Listen("tcp", addr)
	if err != nil {
		return fmt.Errorf("FIX listen: %w", err)
	}
	s.ln = ln
	s.sessionSvc.Start()
	go s.acceptLoop()
	logger.Info("FIX gateway listening", "addr", addr)
	return nil
}

func (s *FixService) Stop() {
	close(s.stopCh)
	s.sessionSvc.Stop()
	if s.ln != nil {
		s.ln.Close()
	}
}

func (s *FixService) acceptLoop() {
	for {
		select {
		case <-s.stopCh:
			return
		default:
			conn, err := s.ln.Accept()
			if err != nil {
				continue
			}
			go s.handleConnection(conn)
		}
	}
}

func (s *FixService) handleConnection(conn net.Conn) {
	defer conn.Close()
	logger.Info("FIX connection accepted", "remote", conn.RemoteAddr().String())
	buf := make([]byte, 4096)
	for {
		select {
		case <-s.stopCh:
			return
		default:
			conn.SetReadDeadline(time.Now().Add(60 * time.Second))
			n, err := conn.Read(buf)
			if err != nil {
				if netErr, ok := err.(net.Error); ok && netErr.Timeout() {
					continue
				}
				logger.Error("FIX read error", "error", err)
				return
			}
			if err := s.sessionSvc.HandleMessage(buf[:n], conn); err != nil {
				logger.Error("FIX message handling error", "error", err)
			}
		}
	}
}

func (s *FixService) SendNewOrder(senderCompID, targetCompID, clOrdID, symbol, side string, volume, price float64) error {
	sess, ok := s.sessionMgr.GetSession(senderCompID, targetCompID)
	if !ok || sess.GetState() != fix.SessionStateLoggedOn {
		return fmt.Errorf("no active session for %s:%s", senderCompID, targetCompID)
	}
	msg := fix.NewMessage()
	msg.Set(fix.TagBeginString, sess.Version)
	msg.Set(fix.TagMsgType, fix.MsgTypeNewOrderSingle)
	msg.Set(fix.TagSenderCompID, senderCompID)
	msg.Set(fix.TagTargetCompID, targetCompID)
	msg.Set(fix.TagMsgSeqNum, fmt.Sprintf("%d", sess.NextOutgoingSeq()))
	msg.Set("11", clOrdID)
	msg.Set("55", symbol)
	msg.Set("54", side)
	msg.Set("38", fmt.Sprintf("%.0f", volume))
	msg.Set("44", fmt.Sprintf("%.5f", price))
	msg.Set("40", "2")
	sess.UpdateLastSent()
	return nil
}

func (s *FixService) SendOrderCancel(senderCompID, targetCompID, orderID, clOrdID, symbol, side string) error {
	sess, ok := s.sessionMgr.GetSession(senderCompID, targetCompID)
	if !ok || sess.GetState() != fix.SessionStateLoggedOn {
		return fmt.Errorf("no active session for %s:%s", senderCompID, targetCompID)
	}
	msg := fix.NewOrderCancel(senderCompID, targetCompID, orderID, clOrdID, symbol, side, sess.Version)
	msg.Set(fix.TagMsgSeqNum, fmt.Sprintf("%d", sess.NextOutgoingSeq()))
	sess.UpdateLastSent()
	return nil
}

func (s *FixService) SendMarketDataRequest(senderCompID, targetCompID, reqID string, symbols []string) error {
	sess, ok := s.sessionMgr.GetSession(senderCompID, targetCompID)
	if !ok || sess.GetState() != fix.SessionStateLoggedOn {
		return fmt.Errorf("no active session for %s:%s", senderCompID, targetCompID)
	}
	msg := fix.NewMarketDataRequest(senderCompID, targetCompID, reqID, symbols, sess.Version)
	msg.Set(fix.TagMsgSeqNum, fmt.Sprintf("%d", sess.NextOutgoingSeq()))
	sess.UpdateLastSent()
	return nil
}

func (s *FixService) GetSessionInfo(senderCompID, targetCompID string) (map[string]interface{}, error) {
	sess, ok := s.sessionMgr.GetSession(senderCompID, targetCompID)
	if !ok {
		return nil, fmt.Errorf("session not found")
	}
	return map[string]interface{}{
		"sender":      sess.SenderCompID,
		"target":      sess.TargetCompID,
		"version":     sess.Version,
		"state":       fmt.Sprintf("%d", sess.GetState()),
		"incomingSeq": sess.IncomingSeq,
		"outgoingSeq": sess.OutgoingSeq,
	}, nil
}
