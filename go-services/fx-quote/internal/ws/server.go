package ws

import (
	"encoding/json"
	"log"
	"net/http"
	"sync"
	"time"

	"github.com/gin-gonic/gin"
	"github.com/gorilla/websocket"

	"fx-quote/internal/model"
	"fx-quote/internal/service"
)

var upgrader = websocket.Upgrader{
	ReadBufferSize:  1024,
	WriteBufferSize: 1024,
	CheckOrigin: func(r *http.Request) bool {
		return true
	},
}

type SubscriptionCommand struct {
	Action  string   `json:"action"`
	Symbols []string `json:"symbols"`
}

type Ticker struct {
	ticker *time.Timer
	ch     chan time.Time
	stop   chan struct{}
	once   sync.Once
}

func NewTicker(d time.Duration) *Ticker {
	t := &Ticker{
		ticker: time.NewTimer(d),
		ch:     make(chan time.Time, 1),
		stop:   make(chan struct{}),
	}
	go func() {
		for {
			select {
			case <-t.stop:
				return
			case tm := <-t.ticker.C:
				t.ch <- tm
				t.ticker.Reset(d)
			}
		}
	}()
	return t
}

func (t *Ticker) C() <-chan time.Time { return t.ch }
func (t *Ticker) Stop() {
	t.once.Do(func() {
		close(t.stop)
		t.ticker.Stop()
	})
}

func jsonMarshal(v interface{}) ([]byte, error) { return json.Marshal(v) }
func jsonUnmarshal(data []byte, v interface{}) error { return json.Unmarshal(data, v) }

type WSServer struct {
	addr       string
	clients    map[*Client]bool
	register   chan *Client
	unregister chan *Client
	broadcast  chan *model.Quote
	mu         sync.RWMutex
}

func NewWSServer(addr string, _ *service.QuoteService) *WSServer {
	return &WSServer{
		addr:       addr,
		clients:    make(map[*Client]bool),
		register:   make(chan *Client),
		unregister: make(chan *Client),
		broadcast:  make(chan *model.Quote, 256),
	}
}

func (s *WSServer) Run() {
	go s.runHub()
	mux := gin.New()
	mux.GET("/ws/quotes", s.handleWebSocket)
	log.Printf("WebSocket server starting on %s", s.addr)
	if err := mux.Run(s.addr); err != nil {
		log.Printf("WebSocket server error: %v", err)
	}
}

func (s *WSServer) runHub() {
	for {
		select {
		case client := <-s.register:
			s.mu.Lock()
			s.clients[client] = true
			s.mu.Unlock()
		case client := <-s.unregister:
			s.mu.Lock()
			if _, ok := s.clients[client]; ok {
				delete(s.clients, client)
				close(client.send)
			}
			s.mu.Unlock()
		case quote := <-s.broadcast:
			s.mu.RLock()
			for client := range s.clients {
				if client.IsSubscribed(quote.Symbol) {
					client.SendQuote(quote)
				}
			}
			s.mu.RUnlock()
		}
	}
}

func (s *WSServer) handleWebSocket(c *gin.Context) {
	conn, err := upgrader.Upgrade(c.Writer, c.Request, nil)
	if err != nil {
		log.Printf("websocket upgrade error: %v", err)
		return
	}
	client := NewClient(conn, s)
	s.register <- client
	go client.WritePump()
	go client.ReadPump()
}

func (s *WSServer) BroadcastQuote(quote *model.Quote) {
	select {
	case s.broadcast <- quote:
	default:
	}
}
