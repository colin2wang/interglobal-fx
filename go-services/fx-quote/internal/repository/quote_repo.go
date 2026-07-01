package repository

import (
	"context"
	"encoding/json"
	"fmt"
	"time"

	"github.com/go-redis/redis/v9"

	"fx-quote/internal/model"
)

type QuoteRepository struct {
	client *redis.Client
}

func NewQuoteRepository(client *redis.Client) *QuoteRepository {
	return &QuoteRepository{client: client}
}

func (r *QuoteRepository) GetQuote(symbol string) (*model.Quote, error) {
	ctx := context.Background()
	key := fmt.Sprintf("quote:%s", symbol)
	data, err := r.client.Get(ctx, key).Bytes()
	if err != nil {
		if err == redis.Nil {
			return nil, nil
		}
		return nil, err
	}
	var quote model.Quote
	if err := json.Unmarshal(data, &quote); err != nil {
		return nil, err
	}
	return &quote, nil
}

func (r *QuoteRepository) SaveQuote(quote *model.Quote, expiration time.Duration) error {
	ctx := context.Background()
	data, err := json.Marshal(quote)
	if err != nil {
		return err
	}
	key := fmt.Sprintf("quote:%s", quote.Symbol)
	return r.client.Set(ctx, key, data, expiration).Err()
}

func (r *QuoteRepository) GetAllQuotes() ([]*model.Quote, error) {
	ctx := context.Background()
	pattern := "quote:*"
	keys, err := r.client.Keys(ctx, pattern).Result()
	if err != nil {
		return nil, err
	}
	var quotes []*model.Quote
	for _, key := range keys {
		data, err := r.client.Get(ctx, key).Bytes()
		if err != nil {
			continue
		}
		var quote model.Quote
		if err := json.Unmarshal(data, &quote); err != nil {
			continue
		}
		quotes = append(quotes, &quote)
	}
	return quotes, nil
}
