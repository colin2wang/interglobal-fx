package fix

import (
	"fmt"
	"sync"
	"time"
)

type SessionState int

const (
	SessionStateDisconnected SessionState = iota
	SessionStateConnected
	SessionStateLoggedOn
	SessionStateLogout
)

type Session struct {
	SenderCompID  string
	TargetCompID  string
	Version       string
	State         SessionState
	IncomingSeq   int64
	OutgoingSeq   int64
	LastReceived  time.Time
	LastSent      time.Time
	HeartbeatInt  int
	TestReqID     string
	mu            sync.Mutex
}

func NewSession(senderCompID, targetCompID, version string, heartbeatInt int) *Session {
	return &Session{
		SenderCompID: senderCompID,
		TargetCompID: targetCompID,
		Version:      version,
		State:        SessionStateDisconnected,
		IncomingSeq:  1,
		OutgoingSeq:  1,
		HeartbeatInt: heartbeatInt,
	}
}

func (s *Session) NextOutgoingSeq() int64 {
	s.mu.Lock()
	defer s.mu.Unlock()
	seq := s.OutgoingSeq
	s.OutgoingSeq++
	return seq
}

func (s *Session) NextIncomingSeq() int64 {
	s.mu.Lock()
	defer s.mu.Unlock()
	seq := s.IncomingSeq
	s.IncomingSeq++
	return seq
}

func (s *Session) SetState(state SessionState) {
	s.mu.Lock()
	defer s.mu.Unlock()
	s.State = state
}

func (s *Session) GetState() SessionState {
	s.mu.Lock()
	defer s.mu.Unlock()
	return s.State
}

func (s *Session) UpdateLastReceived() {
	s.mu.Lock()
	defer s.mu.Unlock()
	s.LastReceived = time.Now()
}

func (s *Session) UpdateLastSent() {
	s.mu.Lock()
	defer s.mu.Unlock()
	s.LastSent = time.Now()
}

func (s *Session) ShouldSendHeartbeat() bool {
	s.mu.Lock()
	defer s.mu.Unlock()
	if s.State != SessionStateLoggedOn {
		return false
	}
	return time.Since(s.LastSent) > time.Duration(s.HeartbeatInt)*time.Second
}

func (s *Session) IsExpired() bool {
	s.mu.Lock()
	defer s.mu.Unlock()
	if s.State != SessionStateLoggedOn {
		return false
	}
	threshold := time.Duration(s.HeartbeatInt*2) * time.Second
	return time.Since(s.LastReceived) > threshold
}

type SessionManager struct {
	sessions map[string]*Session
	mu       sync.RWMutex
}

func NewSessionManager() *SessionManager {
	return &SessionManager{
		sessions: make(map[string]*Session),
	}
}

func (sm *SessionManager) key(sender, target string) string {
	return fmt.Sprintf("%s:%s", sender, target)
}

func (sm *SessionManager) GetSession(senderCompID, targetCompID string) (*Session, bool) {
	sm.mu.RLock()
	defer sm.mu.RUnlock()
	sess, ok := sm.sessions[sm.key(senderCompID, targetCompID)]
	return sess, ok
}

func (sm *SessionManager) CreateSession(senderCompID, targetCompID, version string, heartbeatInt int) *Session {
	sm.mu.Lock()
	defer sm.mu.Unlock()
	sess := NewSession(senderCompID, targetCompID, version, heartbeatInt)
	sm.sessions[sm.key(senderCompID, targetCompID)] = sess
	return sess
}

func (sm *SessionManager) RemoveSession(senderCompID, targetCompID string) {
	sm.mu.Lock()
	defer sm.mu.Unlock()
	delete(sm.sessions, sm.key(senderCompID, targetCompID))
}

func (sm *SessionManager) GetAllSessions() []*Session {
	sm.mu.RLock()
	defer sm.mu.RUnlock()
	result := make([]*Session, 0, len(sm.sessions))
	for _, sess := range sm.sessions {
		result = append(result, sess)
	}
	return result
}

func (sm *SessionManager) HandleLogon(msg *Message) error {
	sender := msg.Get(TagSenderCompID)
	target := msg.Get(TagTargetCompID)
	version := msg.Get(TagBeginString)
	sess, ok := sm.GetSession(sender, target)
	if !ok {
		sess = sm.CreateSession(sender, target, version, 30)
	}
	sess.SetState(SessionStateLoggedOn)
	sess.UpdateLastReceived()
	return nil
}

func (sm *SessionManager) HandleLogout(msg *Message) {
	sender := msg.Get(TagSenderCompID)
	target := msg.Get(TagTargetCompID)
	if sess, ok := sm.GetSession(sender, target); ok {
		sess.SetState(SessionStateLogout)
	}
}

func (sm *SessionManager) HandleHeartbeat(msg *Message) {
	sender := msg.Get(TagSenderCompID)
	target := msg.Get(TagTargetCompID)
	if sess, ok := sm.GetSession(sender, target); ok {
		sess.UpdateLastReceived()
	}
}
