package main

import (
	"fmt"
	"log"
	"os"
	"os/signal"
	"syscall"

	"github.com/gin-gonic/gin"

	"fx-quote/internal/config"
	"fx-quote/internal/handler"
	"fx-quote/internal/repository"
	"fx-quote/internal/service"
	"fx-quote/internal/ws"
	pkafka "fx-quote/pkg/kafka"
	"fx-quote/pkg/logger"
	predis "fx-quote/pkg/redis"
)

func main() {
	configPath := "config.yaml"
	if v := os.Getenv("CONFIG_PATH"); v != "" {
		configPath = v
	}
	cfg, err := config.Load(configPath)
	if err != nil {
		log.Fatalf("load config: %v", err)
	}
	logger.Init("debug")
	logger.Info("starting service", "name", cfg.App.Name)

	rdb, err := predis.NewClient(cfg.Redis.Host, cfg.Redis.Port)
	if err != nil {
		logger.Error("redis connection failed", "error", err)
	}

	var kafkaProducer *pkafka.Producer
	if len(cfg.Kafka.Brokers) > 0 {
		kafkaProducer, err = pkafka.NewProducer(cfg.Kafka.Brokers, cfg.Kafka.Topic)
		if err != nil {
			logger.Error("kafka producer init failed", "error", err)
		}
	}

	quoteRepo := repository.NewQuoteRepository(rdb)
	klineRepo := repository.NewKlineRepository(rdb)
	klineService := service.NewKlineService(klineRepo)
	quoteService := service.NewQuoteService(quoteRepo, klineService, kafkaProducer)
	quoteHandler := handler.NewQuoteHandler(quoteService)
	klineHandler := handler.NewKlineHandler(klineService)

	wsServer := ws.NewWSServer(fmt.Sprintf(":%d", cfg.App.Port+100), quoteService)
	go wsServer.Run()

	router := gin.New()
	router.Use(gin.Recovery())
	if cfg.App.Mode == "debug" {
		router.Use(gin.Logger())
	}

	v1 := router.Group("/api/v1")
	{
		v1.GET("/quotes/:symbol", quoteHandler.GetQuote)
		v1.GET("/quotes", quoteHandler.GetQuotes)
		v1.GET("/klines/:symbol", klineHandler.GetKlines)
	}

	addr := fmt.Sprintf(":%d", cfg.App.Port)
	go func() {
		logger.Info("HTTP server starting", "addr", addr)
		if err := router.Run(addr); err != nil {
			logger.Error("HTTP server error", "error", err)
		}
	}()

	quit := make(chan os.Signal, 1)
	signal.Notify(quit, syscall.SIGINT, syscall.SIGTERM)
	<-quit
	logger.Info("shutting down service")
}
