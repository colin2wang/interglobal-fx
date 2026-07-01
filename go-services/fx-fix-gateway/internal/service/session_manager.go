package service

import (
	"net"
	"sync"
	"time"

	"fx-fix-gateway/internal/config"
	"fx-fix-gateway/internal/protocol/fix"
	"fx-fix-gateway/pkg/logger"
)

type SessionManagerService struct {
	parser        *fix.Parser
	sessionMgr    *fix.SessionManager
	cfg           *config.Config
	connections   map[string]net.Conn
	mu            sync.RWMutex
	stopCh        chan struct{}
}

func NewSessionManagerService(cfg *config.Config) *SessionManagerService {
	return &SessionManagerService{
		parser:      fix.NewParser(),
		sessionMgr:  fix.NewSessionManager(),
		cfg:         cfg,
		connections: make(map[string]net.Conn),
		stopCh:      make(chan struct{}),
	}
}

func (s *SessionManagerService) Start() {
	go s.heartbeatLoop()
}

func (s *SessionManagerService) Stop() {
	close(s.stopCh)
}

func (s *SessionManagerService) heartbeatLoop() {
	interval := time.Duration(s.cfg.FIX.HeartbeatInterval) * time.Second
	ticker := time.NewTicker(interval)
	defer ticker.Stop()
	for {
		select {
		case <-s.stopCh:
			return
		case <-ticker.C:
			s.checkHeartbeats()
		}
	}
}

func (s *SessionManagerService) checkHeartbeats() {
	sessions := s.sessionMgr.GetAllSessions()
	for _, sess := range sessions {
		if sess.ShouldSendHeartbeat() {
			hb := fix.NewHeartbeat(sess.SenderCompID, sess.TargetCompID, sess.Version)
			s.sendMessage(sess, hb)
		}
		if sess.IsExpired() {
			logger.Error("session expired", "sender", sess.SenderCompID, "target", sess.TargetCompID)
			sess.SetState(fix.SessionStateDisconnected)
		}
	}
}

func (s *SessionManagerService) HandleMessage(data []byte, conn net.Conn) error {
	msg, err := s.parser.Parse(data)
	if err != nil {
		return err
	}
	if err := s.parser.Validate(msg); err != nil {
		return err
	}
	sender := msg.Get(fix.TagSenderCompID)
	target := msg.Get(fix.TagTargetCompID)
	key := sender + ":" + target
	s.mu.Lock()
	s.connections[key] = conn
	s.mu.Unlock()
	switch msg.GetMsgType() {
	case fix.MsgTypeLogon:
		return s.handleLogon(msg, conn)
	case fix.MsgTypeLogout:
		s.handleLogout(msg)
		return nil
	case fix.MsgTypeHeartbeat:
		s.sessionMgr.HandleHeartbeat(msg)
		return nil
	case fix.MsgTypeTestRequest:
		return s.handleTestRequest(msg, conn)
	case fix.MsgTypeNewOrderSingle:
		return s.handleNewOrder(msg, conn)
	case fix.MsgTypeOrderCancel:
		return s.handleOrderCancel(msg, conn)
	case fix.MsgTypeMarketDataRequest:
		return s.handleMarketDataRequest(msg, conn)
	default:
		logger.Debug("unhandled message type", "type", msg.GetMsgType())
		return nil
	}
}

func (s *SessionManagerService) handleLogon(msg *fix.Message, conn net.Conn) error {
	if err := s.sessionMgr.HandleLogon(msg); err != nil {
		return err
	}
	sender := msg.Get(fix.TagSenderCompID)
	target := msg.Get(fix.TagTargetCompID)
	version := msg.Get(fix.TagBeginString)
	resp := fix.NewLogon(target, sender, "", "", version)
	_ = conn
	s.sendMessageToConn(conn, resp)
	logger.Info("logon accepted", "sender", sender, "target", target)
	return nil
}

func (s *SessionManagerService) handleLogout(msg *fix.Message) {
	s.sessionMgr.HandleLogout(msg)
	logger.Info("logout received", "sender", msg.Get(fix.TagSenderCompID))
}

func (s *SessionManagerService) handleTestRequest(msg *fix.Message, conn net.Conn) error {
	sender := msg.Get(fix.TagSenderCompID)
	target := msg.Get(fix.TagTargetCompID)
	version := msg.Get(fix.TagBeginString)
	testReqID := msg.Get("112")
	hb := fix.NewHeartbeat(target, sender, version)
	hb.Set("112", testReqID)
	s.sendMessageToConn(conn, hb)
	return nil
}

func (s *SessionManagerService) handleNewOrder(msg *fix.Message, conn net.Conn) error {
	logger.Info("new order received",
		"clOrdID", msg.Get("11"),
		"symbol", msg.Get("55"),
		"side", msg.Get("54"),
	)
	return nil
}

func (s *SessionManagerService) handleOrderCancel(msg *fix.Message, conn net.Conn) error {
	logger.Info("order cancel received",
		"orderID", msg.Get("41"),
		"clOrdID", msg.Get("11"),
	)
	return nil
}

func (s *SessionManagerService) handleMarketDataRequest(msg *fix.Message, conn net.Conn) error {
	logger.Info("market data request",
		"reqID", msg.Get("262"),
		"symbol", msg.Get("55"),
	)
	return nil
}

func (s *SessionManagerService) sendMessage(sess *fix.Session, msg *fix.Message) {
	key := sess.SenderCompID + ":" + sess.TargetCompID
	s.mu.RLock()
	conn, ok := s.connections[key]
	s.mu.RUnlock()
	if ok {
		s.sendMessageToConn(conn, msg)
	}
}

func (s *SessionManagerService) sendMessageToConn(conn net.Conn, msg *fix.Message) {
	data := msg.ToBytes()
	if _, err := conn.Write(data); err != nil {
		logger.Error("send message failed", "error", err)
	}
}

func (s *SessionManagerService) GetSessionState(senderCompID, targetCompID string) (string, bool) {
	sess, ok := s.sessionMgr.GetSession(senderCompID, targetCompID)
	if !ok {
		return "", false
	}
	states := map[fix.SessionState]string{
		fix.SessionStateDisconnected: "disconnected",
		fix.SessionStateConnected:    "connected",
		fix.SessionStateLoggedOn:     "logged_on",
		fix.SessionStateLogout:       "logout",
	}
	return states[sess.GetState()], true
}


