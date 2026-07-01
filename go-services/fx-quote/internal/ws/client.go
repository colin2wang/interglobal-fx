package ws

import (
	"log"
	"sync"

	"github.com/gorilla/websocket"

	"fx-quote/internal/model"
)

const (
	writeWait  = 10 * 1000 * 1000 * 1000
	pongWait   = 60 * 1000 * 1000 * 1000
	pingPeriod = (pongWait * 9) / 10
)

type Client struct {
	server     *WSServer
	conn       *websocket.Conn
	send       chan []byte
	symbols    map[string]bool
	mu         sync.Mutex
}

func NewClient(conn *websocket.Conn, server *WSServer) *Client {
	return &Client{
		server:  server,
		conn:    conn,
		send:    make(chan []byte, 256),
		symbols: make(map[string]bool),
	}
}

func (c *Client) ReadPump() {
	defer func() {
		c.server.unregister <- c
		c.conn.Close()
	}()
	c.conn.SetReadLimit(512)
	c.conn.SetReadDeadline(nil)
	c.conn.SetPongHandler(func(string) error {
		return nil
	})
	for {
		_, message, err := c.conn.ReadMessage()
		if err != nil {
			break
		}
		c.handleMessage(message)
	}
}

func (c *Client) WritePump() {
	ticker := NewTicker(pingPeriod)
	defer func() {
		ticker.Stop()
		c.conn.Close()
	}()
	for {
		select {
		case message, ok := <-c.send:
			c.conn.SetWriteDeadline(nil)
			if !ok {
				c.conn.WriteMessage(websocket.CloseMessage, []byte{})
				return
			}
			if err := c.conn.WriteMessage(websocket.TextMessage, message); err != nil {
				return
			}
		case <-ticker.C():
			c.conn.SetWriteDeadline(nil)
			if err := c.conn.WriteMessage(websocket.PingMessage, nil); err != nil {
				return
			}
		}
	}
}

func (c *Client) handleMessage(message []byte) {
	var cmd SubscriptionCommand
	if err := jsonUnmarshal(message, &cmd); err != nil {
		return
	}
	c.mu.Lock()
	defer c.mu.Unlock()
	switch cmd.Action {
	case "subscribe":
		for _, sym := range cmd.Symbols {
			c.symbols[sym] = true
		}
	case "unsubscribe":
		for _, sym := range cmd.Symbols {
			delete(c.symbols, sym)
		}
	}
}

func (c *Client) IsSubscribed(symbol string) bool {
	c.mu.Lock()
	defer c.mu.Unlock()
	return c.symbols[symbol]
}

func (c *Client) SendQuote(quote *model.Quote) {
	data, err := jsonMarshal(quote)
	if err != nil {
		log.Printf("marshal quote error: %v", err)
		return
	}
	select {
	case c.send <- data:
	default:
	}
}
