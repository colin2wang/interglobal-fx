package fix

import (
	"fmt"
	"strings"
)

type Parser struct{}

func NewParser() *Parser {
	return &Parser{}
}

func (p *Parser) Parse(data []byte) (*Message, error) {
	str := string(data)
	str = strings.TrimRight(str, SOH)
	parts := strings.Split(str, SOH)
	msg := NewMessage()
	for _, part := range parts {
		idx := strings.Index(part, "=")
		if idx < 0 {
			continue
		}
		tag := part[:idx]
		value := part[idx+1:]
		msg.Set(tag, value)
	}
	return msg, nil
}

func (p *Parser) Validate(msg *Message) error {
	version := msg.Get(TagBeginString)
	if version != TagBeginStringFix42 && version != TagBeginStringFix44 {
		return fmt.Errorf("unsupported FIX version: %s", version)
	}
	if msg.Get(TagMsgType) == "" {
		return fmt.Errorf("missing MsgType")
	}
	if msg.Get(TagSenderCompID) == "" {
		return fmt.Errorf("missing SenderCompID")
	}
	if msg.Get(TagTargetCompID) == "" {
		return fmt.Errorf("missing TargetCompID")
	}
	return nil
}

func (p *Parser) CalculateBodyLength(msg *Message) int {
	var sb strings.Builder
	for _, f := range msg.Fields {
		if f.Tag == TagBeginString || f.Tag == TagBodyLength {
			continue
		}
		sb.WriteString(f.Tag)
		sb.WriteString("=")
		sb.WriteString(f.Value)
		sb.WriteString(SOH)
	}
	return sb.Len()
}

func (p *Parser) CalculateChecksum(data []byte) int {
	sum := 0
	for i := 0; i < len(data); i++ {
		sum += int(data[i])
	}
	return sum % 256
}
