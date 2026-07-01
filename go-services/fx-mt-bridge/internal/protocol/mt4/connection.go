package mt4

import (
	"bufio"
	"fmt"
	"net"
	"sync"
	"time"
)

type Connection struct {
	conn     net.Conn
	reader   *bufio.Reader
	mu       sync.Mutex
	connected bool
	host     string
	port     int
	login    string
	password string
	timeout  time.Duration
}

func NewConnection(host string, port int, login, password string, timeoutSecs int) *Connection {
	return &Connection{
		host:     host,
		port:     port,
		login:    login,
		password: password,
		timeout:  time.Duration(timeoutSecs) * time.Second,
	}
}

func (c *Connection) Connect() error {
	addr := fmt.Sprintf("%s:%d", c.host, c.port)
	conn, err := net.DialTimeout("tcp", addr, c.timeout)
	if err != nil {
		return fmt.Errorf("connect to MT4: %w", err)
	}
	c.conn = conn
	c.reader = bufio.NewReader(conn)
	c.connected = true
	return nil
}

func (c *Connection) Disconnect() error {
	if c.conn != nil {
		c.connected = false
		return c.conn.Close()
	}
	return nil
}

func (c *Connection) Send(data []byte) error {
	c.mu.Lock()
	defer c.mu.Unlock()
	if !c.connected {
		return fmt.Errorf("not connected")
	}
	c.conn.SetWriteDeadline(time.Now().Add(c.timeout))
	_, err := c.conn.Write(data)
	return err
}

func (c *Connection) Read() ([]byte, error) {
	c.mu.Lock()
	defer c.mu.Unlock()
	if !c.connected {
		return nil, fmt.Errorf("not connected")
	}
	c.conn.SetReadDeadline(time.Now().Add(c.timeout))
	line, err := c.reader.ReadBytes('\n')
	if err != nil {
		return nil, err
	}
	return line, nil
}

func (c *Connection) IsConnected() bool {
	return c.connected
}
