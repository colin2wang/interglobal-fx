package mt4

import (
	"encoding/json"
	"fmt"
	"sync"
	"time"
)

type Manager struct {
	conn        *Connection
	mu          sync.Mutex
	connected   bool
	lastPing    time.Time
	requestID   int64
}

func NewManager(conn *Connection) *Manager {
	return &Manager{conn: conn}
}

func (m *Manager) Connect() error {
	if err := m.conn.Connect(); err != nil {
		return err
	}
	m.connected = true
	return nil
}

func (m *Manager) Disconnect() error {
	m.connected = false
	return m.conn.Disconnect()
}

func (m *Manager) IsConnected() bool {
	return m.connected
}

type MT4Request struct {
	Command string      `json:"command"`
	ID      int64       `json:"id"`
	Data    interface{} `json:"data"`
}

type MT4Response struct {
	ID      int64           `json:"id"`
	Status  string          `json:"status"`
	Data    json.RawMessage `json:"data"`
	Error   string          `json:"error,omitempty"`
}

type AccountInfo struct {
	Login           int64   `json:"login"`
	Name            string  `json:"name"`
	Group           string  `json:"group"`
	Currency        string  `json:"currency"`
	Balance         float64 `json:"balance"`
	Equity          float64 `json:"equity"`
	Margin          float64 `json:"margin"`
	FreeMargin      float64 `json:"free_margin"`
	Leverage        int     `json:"leverage"`
	Profit          float64 `json:"profit"`
}

type OrderInfo struct {
	Order     int64   `json:"order"`
	Symbol    string  `json:"symbol"`
	Type      string  `json:"type"`
	Volume    float64 `json:"volume"`
	Price     float64 `json:"price"`
	StopLoss  float64 `json:"stop_loss"`
	TakeProfit float64 `json:"take_profit"`
	Comment   string  `json:"comment"`
	Time      int64   `json:"time"`
	Status    string  `json:"status"`
}

type PositionInfo struct {
	Order     int64   `json:"order"`
	Symbol    string  `json:"symbol"`
	Type      string  `json:"type"`
	Volume    float64 `json:"volume"`
	OpenPrice float64 `json:"open_price"`
	Current   float64 `json:"current_price"`
	Profit    float64 `json:"profit"`
	Swap      float64 `json:"swap"`
}

func (m *Manager) GetAccountInfo(login int64) (*AccountInfo, error) {
	m.mu.Lock()
	m.requestID++
	id := m.requestID
	m.mu.Unlock()

	req := MT4Request{
		Command: "get_account",
		ID:      id,
		Data:    map[string]int64{"login": login},
	}
	resp, err := m.sendRequest(req)
	if err != nil {
		return nil, err
	}
	var info AccountInfo
	if err := json.Unmarshal(resp.Data, &info); err != nil {
		return nil, fmt.Errorf("parse account info: %w", err)
	}
	return &info, nil
}

func (m *Manager) OpenOrder(login int64, symbol string, orderType string, volume float64, price float64, sl, tp float64, comment string) (*OrderInfo, error) {
	m.mu.Lock()
	m.requestID++
	id := m.requestID
	m.mu.Unlock()

	req := MT4Request{
		Command: "open_order",
		ID:      id,
		Data: map[string]interface{}{
			"login":    login,
			"symbol":   symbol,
			"type":     orderType,
			"volume":   volume,
			"price":    price,
			"sl":       sl,
			"tp":       tp,
			"comment":  comment,
		},
	}
	resp, err := m.sendRequest(req)
	if err != nil {
		return nil, err
	}
	var order OrderInfo
	if err := json.Unmarshal(resp.Data, &order); err != nil {
		return nil, fmt.Errorf("parse order info: %w", err)
	}
	return &order, nil
}

func (m *Manager) CloseOrder(orderID int64) error {
	m.mu.Lock()
	m.requestID++
	id := m.requestID
	m.mu.Unlock()

	req := MT4Request{
		Command: "close_order",
		ID:      id,
		Data:    map[string]int64{"order": orderID},
	}
	_, err := m.sendRequest(req)
	return err
}

func (m *Manager) GetPositions(login int64) ([]PositionInfo, error) {
	m.mu.Lock()
	m.requestID++
	id := m.requestID
	m.mu.Unlock()

	req := MT4Request{
		Command: "get_positions",
		ID:      id,
		Data:    map[string]int64{"login": login},
	}
	resp, err := m.sendRequest(req)
	if err != nil {
		return nil, err
	}
	var positions []PositionInfo
	if err := json.Unmarshal(resp.Data, &positions); err != nil {
		return nil, fmt.Errorf("parse positions: %w", err)
	}
	return positions, nil
}

func (m *Manager) GetOrders(login int64) ([]OrderInfo, error) {
	m.mu.Lock()
	m.requestID++
	id := m.requestID
	m.mu.Unlock()

	req := MT4Request{
		Command: "get_orders",
		ID:      id,
		Data:    map[string]int64{"login": login},
	}
	resp, err := m.sendRequest(req)
	if err != nil {
		return nil, err
	}
	var orders []OrderInfo
	if err := json.Unmarshal(resp.Data, &orders); err != nil {
		return nil, fmt.Errorf("parse orders: %w", err)
	}
	return orders, nil
}

func (m *Manager) sendRequest(req MT4Request) (*MT4Response, error) {
	data, err := json.Marshal(req)
	if err != nil {
		return nil, fmt.Errorf("marshal request: %w", err)
	}
	data = append(data, '\n')
	if err := m.conn.Send(data); err != nil {
		return nil, fmt.Errorf("send request: %w", err)
	}
	respBytes, err := m.conn.Read()
	if err != nil {
		return nil, fmt.Errorf("read response: %w", err)
	}
	var resp MT4Response
	if err := json.Unmarshal(respBytes, &resp); err != nil {
		return nil, fmt.Errorf("unmarshal response: %w", err)
	}
	if resp.Error != "" {
		return nil, fmt.Errorf("MT4 error: %s", resp.Error)
	}
	return &resp, nil
}
