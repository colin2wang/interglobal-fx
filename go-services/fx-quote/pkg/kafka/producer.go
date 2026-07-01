package kafka

import (
	"context"
	"fmt"

	kafkago "github.com/segmentio/kafka-go"
)

type Producer struct {
	writer *kafkago.Writer
}

func NewProducer(brokers []string, topic string) (*Producer, error) {
	w := &kafkago.Writer{
		Addr:     kafkago.TCP(brokers...),
		Topic:    topic,
		Balancer: &kafkago.LeastBytes{},
	}
	p := &Producer{writer: w}
	return p, nil
}

func (p *Producer) Publish(ctx context.Context, key, value []byte) error {
	msg := kafkago.Message{
		Key:   key,
		Value: value,
	}
	if err := p.writer.WriteMessages(ctx, msg); err != nil {
		return fmt.Errorf("kafka publish failed: %w", err)
	}
	return nil
}

func (p *Producer) Close() error {
	return p.writer.Close()
}
