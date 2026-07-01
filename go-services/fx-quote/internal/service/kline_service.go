package service

import (
	"time"

	"fx-quote/internal/model"
	"fx-quote/internal/repository"
)

var periodDurations = map[string]time.Duration{
	"1m":  time.Minute,
	"5m":  5 * time.Minute,
	"15m": 15 * time.Minute,
	"1h":  time.Hour,
	"4h":  4 * time.Hour,
	"1d":  24 * time.Hour,
}

type KlineService struct {
	klineRepo *repository.KlineRepository
}

func NewKlineService(klineRepo *repository.KlineRepository) *KlineService {
	return &KlineService{klineRepo: klineRepo}
}

func (s *KlineService) GetKlines(symbol, period string, limit int) ([]*model.Kline, error) {
	if limit <= 0 {
		limit = 100
	}
	return s.klineRepo.GetKlines(symbol, period, limit)
}

func (s *KlineService) UpdateKlineFromTick(tick *model.TickEvent) error {
	for period := range periodDurations {
		if err := s.updatePeriodKline(tick, period); err != nil {
			return err
		}
	}
	return nil
}

func (s *KlineService) updatePeriodKline(tick *model.TickEvent, period string) error {
	dur, ok := periodDurations[period]
	if !ok {
		return nil
	}
	tickTime := time.UnixMilli(tick.Timestamp)
	periodStart := tickTime.Truncate(dur).UnixMilli()

	existing, err := s.klineRepo.GetLatestKline(tick.Symbol, period)
	if err != nil {
		return err
	}

	if existing != nil && existing.Timestamp == periodStart {
		existing.High = max(existing.High, tick.Bid, tick.Ask)
		existing.Low = min(existing.Low, tick.Bid, tick.Ask)
		existing.Close = (tick.Bid + tick.Ask) / 2
		existing.Volume += tick.Volume
		return s.klineRepo.SaveKline(existing)
	}

	newKline := &model.Kline{
		Symbol:    tick.Symbol,
		Period:    period,
		Open:      (tick.Bid + tick.Ask) / 2,
		High:      max(tick.Bid, tick.Ask),
		Low:       min(tick.Bid, tick.Ask),
		Close:     (tick.Bid + tick.Ask) / 2,
		Volume:    tick.Volume,
		Timestamp: periodStart,
	}
	return s.klineRepo.SaveKline(newKline)
}
