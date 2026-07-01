package repository

import (
	"context"
	"encoding/json"
	"fmt"
	"strconv"

	"github.com/go-redis/redis/v9"

	"fx-quote/internal/model"
)

type KlineRepository struct {
	client *redis.Client
}

func NewKlineRepository(client *redis.Client) *KlineRepository {
	return &KlineRepository{client: client}
}

func (r *KlineRepository) klineKey(symbol, period string) string {
	return fmt.Sprintf("kline:%s:%s", symbol, period)
}

func (r *KlineRepository) GetKlines(symbol, period string, limit int) ([]*model.Kline, error) {
	ctx := context.Background()
	key := r.klineKey(symbol, period)
	results, err := r.client.ZRevRangeWithScores(ctx, key, 0, int64(limit-1)).Result()
	if err != nil {
		return nil, err
	}
	var klines []*model.Kline
	for i := len(results) - 1; i >= 0; i-- {
		data, ok := results[i].Member.(string)
		if !ok {
			continue
		}
		var kline model.Kline
		if err := json.Unmarshal([]byte(data), &kline); err != nil {
			continue
		}
		klines = append(klines, &kline)
	}
	return klines, nil
}

func (r *KlineRepository) SaveKline(kline *model.Kline) error {
	ctx := context.Background()
	data, err := json.Marshal(kline)
	if err != nil {
		return err
	}
	key := r.klineKey(kline.Symbol, kline.Period)
	score := float64(kline.Timestamp)
	return r.client.ZAdd(ctx, key, redis.Z{
		Score:  score,
		Member: string(data),
	}).Err()
}

func (r *KlineRepository) GetLatestKline(symbol, period string) (*model.Kline, error) {
	ctx := context.Background()
	key := r.klineKey(symbol, period)
	results, err := r.client.ZRevRangeWithScores(ctx, key, 0, 0).Result()
	if err != nil {
		return nil, err
	}
	if len(results) == 0 {
		return nil, nil
	}
	member, ok := results[0].Member.(string)
	if !ok {
		return nil, fmt.Errorf("invalid kline data")
	}
	var kline model.Kline
	if err := json.Unmarshal([]byte(member), &kline); err != nil {
		return nil, err
	}
	_ = strconv.FormatFloat(results[0].Score, 'f', -1, 64)
	return &kline, nil
}
