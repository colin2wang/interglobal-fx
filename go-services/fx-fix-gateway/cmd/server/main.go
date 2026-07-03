package main

import (
	"fmt"
	"log"
	"os"
	"os/signal"
	"syscall"

	"github.com/gin-gonic/gin"

	"fx-fix-gateway/internal/config"
	"fx-fix-gateway/internal/handler"
	"fx-fix-gateway/internal/service"
	"fx-fix-gateway/pkg/logger"
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

	fixService := service.NewFixService(cfg)
	if err := fixService.Start(); err != nil {
		logger.Error("FIX gateway start failed", "error", err)
	}
	defer fixService.Stop()

	fixHandler := handler.NewFixHandler(fixService)

	router := gin.New()
	router.Use(gin.Recovery())
	if cfg.App.Mode == "debug" {
		router.Use(gin.Logger())
	}

	v1 := router.Group("/api/v1")
	{
		v1.GET("/fix/status", fixHandler.GetStatus)
		v1.GET("/fix/session", fixHandler.GetSession)
		v1.POST("/fix/order", fixHandler.SendNewOrder)
		v1.POST("/fix/order/cancel", fixHandler.SendOrderCancel)
		v1.POST("/fix/marketdata", fixHandler.SendMarketDataRequest)
	}

	router.GET("/health", func(c *gin.Context) {
		c.JSON(200, gin.H{
			"status":  "UP",
			"service": cfg.App.Name,
			"checks":  map[string]string{},
		})
	})

	addr := fmt.Sprintf(":%d", cfg.App.Port)
	logger.Info("HTTP server starting", "addr", addr)
	go func() {
		if err := router.Run(addr); err != nil {
			logger.Error("HTTP server error", "error", err)
		}
	}()

	quit := make(chan os.Signal, 1)
	signal.Notify(quit, syscall.SIGINT, syscall.SIGTERM)
	<-quit
	logger.Info("shutting down service")
}
