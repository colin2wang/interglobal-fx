package service

import (
	"time"

	"fx-quote/internal/model"
)

type AggregatorService struct{}

func NewAggregatorService() *AggregatorService {
	return &AggregatorService{}
}

func (s *AggregatorService) AggregateQuote(symbol string, quotes []*model.Quote) *model.Quote {
	if len(quotes) == 0 {
		return nil
	}
	var totalBid, totalAsk float64
	var latestTimestamp int64
	for _, q := range quotes {
		totalBid += q.Bid
		totalAsk += q.Ask
		if q.Timestamp > latestTimestamp {
			latestTimestamp = q.Timestamp
		}
	}
	avgBid := totalBid / float64(len(quotes))
	avgAsk := totalAsk / float64(len(quotes))
	return &model.Quote{
		Symbol:    symbol,
		Bid:       avgBid,
		Ask:       avgAsk,
		Spread:    avgAsk - avgBid,
		Timestamp: latestTimestamp,
		Source:    "aggregated",
	}
}

func (s *AggregatorService) DefaultQuote(symbol string) *model.Quote {
	return &model.Quote{
		Symbol:    symbol,
		Bid:       0,
		Ask:       0,
		Spread:    0,
		Timestamp: time.Now().UnixMilli(),
		Source:    "default",
	}
}
