package fix

import (
	"fmt"
	"strings"
)

const (
	SOH = "\x01"

	TagBeginString      = "8"
	TagBodyLength       = "9"
	TagMsgType          = "35"
	TagMsgSeqNum        = "34"
	TagSenderCompID     = "49"
	TagTargetCompID     = "56"
	TagSendingTime      = "52"
	TagPossDupFlag      = "43"
	TagBeginStringFix42 = "FIX.4.2"
	TagBeginStringFix44 = "FIX.4.4"

	MsgTypeLogon          = "A"
	MsgTypeLogout         = "5"
	MsgTypeHeartbeat      = "0"
	MsgTypeTestRequest    = "1"
	MsgTypeResendRequest  = "2"
	MsgTypeReject         = "3"
	MsgTypeSequenceReset  = "4"
	MsgTypeNewOrderSingle = "D"
	MsgTypeOrderCancel    = "F"
	MsgTypeExecutionReport = "8"
	MsgTypeMarketDataRequest  = "V"
	MsgTypeMarketDataSnapshot = "W"
	MsgTypeMarketDataIncremental = "X"
)

type Field struct {
	Tag   string
	Value string
}

type Message struct {
	Fields []Field
}

func NewMessage() *Message {
	return &Message{Fields: make([]Field, 0)}
}

func (m *Message) Set(tag, value string) {
	for i, f := range m.Fields {
		if f.Tag == tag {
			m.Fields[i].Value = value
			return
		}
	}
	m.Fields = append(m.Fields, Field{Tag: tag, Value: value})
}

func (m *Message) Get(tag string) string {
	for _, f := range m.Fields {
		if f.Tag == tag {
			return f.Value
		}
	}
	return ""
}

func (m *Message) GetMsgType() string {
	return m.Get(TagMsgType)
}

func (m *Message) ToBytes() []byte {
	var sb strings.Builder
	for _, f := range m.Fields {
		sb.WriteString(f.Tag)
		sb.WriteString("=")
		sb.WriteString(f.Value)
		sb.WriteString(SOH)
	}
	return []byte(sb.String())
}

func (m *Message) String() string {
	return string(m.ToBytes())
}

func NewLogon(senderCompID, targetCompID, username, password, version string) *Message {
	msg := NewMessage()
	msg.Set(TagBeginString, version)
	msg.Set(TagMsgType, MsgTypeLogon)
	msg.Set(TagSenderCompID, senderCompID)
	msg.Set(TagTargetCompID, targetCompID)
	msg.Set("98", "0")
	msg.Set("108", "30")
	msg.Set("553", username)
	msg.Set("554", password)
	return msg
}

func NewLogout(senderCompID, targetCompID, reason, version string) *Message {
	msg := NewMessage()
	msg.Set(TagBeginString, version)
	msg.Set(TagMsgType, MsgTypeLogout)
	msg.Set(TagSenderCompID, senderCompID)
	msg.Set(TagTargetCompID, targetCompID)
	msg.Set("58", reason)
	return msg
}

func NewHeartbeat(senderCompID, targetCompID, version string) *Message {
	msg := NewMessage()
	msg.Set(TagBeginString, version)
	msg.Set(TagMsgType, MsgTypeHeartbeat)
	msg.Set(TagSenderCompID, senderCompID)
	msg.Set(TagTargetCompID, targetCompID)
	return msg
}

func NewTestRequest(senderCompID, targetCompID, testReqID, version string) *Message {
	msg := NewMessage()
	msg.Set(TagBeginString, version)
	msg.Set(TagMsgType, MsgTypeTestRequest)
	msg.Set(TagSenderCompID, senderCompID)
	msg.Set(TagTargetCompID, targetCompID)
	msg.Set("112", testReqID)
	return msg
}

func NewOrderCancel(senderCompID, targetCompID, orderID, clOrdID, symbol, side, version string) *Message {
	msg := NewMessage()
	msg.Set(TagBeginString, version)
	msg.Set(TagMsgType, MsgTypeOrderCancel)
	msg.Set(TagSenderCompID, senderCompID)
	msg.Set(TagTargetCompID, targetCompID)
	msg.Set("41", orderID)
	msg.Set("11", clOrdID)
	msg.Set("55", symbol)
	msg.Set("54", side)
	return msg
}

func NewExecutionReport(senderCompID, targetCompID, orderID, execID, execType, ordStatus, symbol, side string, volume, price float64, version string) *Message {
	msg := NewMessage()
	msg.Set(TagBeginString, version)
	msg.Set(TagMsgType, MsgTypeExecutionReport)
	msg.Set(TagSenderCompID, senderCompID)
	msg.Set(TagTargetCompID, targetCompID)
	msg.Set("37", orderID)
	msg.Set("17", execID)
	msg.Set("150", execType)
	msg.Set("39", ordStatus)
	msg.Set("55", symbol)
	msg.Set("54", side)
	msg.Set(fmt.Sprintf("%d", 14), fmt.Sprintf("%.0f", volume))
	msg.Set(fmt.Sprintf("%d", 6), fmt.Sprintf("%.5f", price))
	return msg
}

func NewMarketDataRequest(senderCompID, targetCompID, reqID string, symbols []string, version string) *Message {
	msg := NewMessage()
	msg.Set(TagBeginString, version)
	msg.Set(TagMsgType, MsgTypeMarketDataRequest)
	msg.Set(TagSenderCompID, senderCompID)
	msg.Set(TagTargetCompID, targetCompID)
	msg.Set("262", reqID)
	msg.Set("263", "1")
	msg.Set("264", "0")
	for _, sym := range symbols {
		msg.Set("55", sym)
	}
	return msg
}

func NewMarketDataSnapshot(senderCompID, targetCompID, reqID, symbol string, bid, ask float64, version string) *Message {
	msg := NewMessage()
	msg.Set(TagBeginString, version)
	msg.Set(TagMsgType, MsgTypeMarketDataSnapshot)
	msg.Set(TagSenderCompID, senderCompID)
	msg.Set(TagTargetCompID, targetCompID)
	msg.Set("262", reqID)
	msg.Set("55", symbol)
	msg.Set("132", fmt.Sprintf("%.5f", bid))
	msg.Set("133", fmt.Sprintf("%.5f", ask))
	msg.Set("134", "1000000")
	msg.Set("135", "1000000")
	return msg
}
