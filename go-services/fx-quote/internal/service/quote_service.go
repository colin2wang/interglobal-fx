package service

import (
	"context"
	"encoding/json"
	"fmt"
	"time"

	"fx-quote/internal/model"
	"fx-quote/internal/repository"
	pkafka "fx-quote/pkg/kafka"
)

type QuoteService struct {
	quoteRepo    *repository.QuoteRepository
	klineService *KlineService
	kafkaProducer *pkafka.Producer
}

func NewQuoteService(
	quoteRepo *repository.QuoteRepository,
	klineService *KlineService,
	kafkaProducer *pkafka.Producer,
) *QuoteService {
	return &QuoteService{
		quoteRepo:     quoteRepo,
		klineService:  klineService,
		kafkaProducer: kafkaProducer,
	}
}

func (s *QuoteService) GetQuote(symbol string) (*model.Quote, error) {
	return s.quoteRepo.GetQuote(symbol)
}

func (s *QuoteService) GetQuotes() ([]*model.Quote, error) {
	return s.quoteRepo.GetAllQuotes()
}

func (s *QuoteService) ProcessTick(tick *model.TickEvent) error {
	quote := &model.Quote{
		Symbol:    tick.Symbol,
		Bid:       tick.Bid,
		Ask:       tick.Ask,
		Spread:    tick.Ask - tick.Bid,
		Timestamp: tick.Timestamp,
		Source:    tick.Source,
	}
	if err := s.quoteRepo.SaveQuote(quote, 5*time.Minute); err != nil {
		return fmt.Errorf("save quote: %w", err)
	}
	if s.kafkaProducer != nil {
		data, err := json.Marshal(tick)
		if err == nil {
			_ = s.kafkaProducer.Publish(context.Background(), []byte(tick.Symbol), data)
		}
	}
	if err := s.klineService.UpdateKlineFromTick(tick); err != nil {
		return fmt.Errorf("update kline: %w", err)
	}
	return nil
}
