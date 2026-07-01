package main

import (
	"fmt"
	"log"
	"os"
	"os/signal"
	"syscall"

	"github.com/gin-gonic/gin"

	"fx-risk/internal/config"
	"fx-risk/internal/handler"
	"fx-risk/internal/service"
	"fx-risk/pkg/logger"
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

	riskService := service.NewRiskService(cfg)
	riskHandler := handler.NewRiskHandler(riskService)

	router := gin.New()
	router.Use(gin.Recovery())
	if cfg.App.Mode == "debug" {
		router.Use(gin.Logger())
	}

	v1 := router.Group("/api/v1")
	{
		v1.POST("/risk/check", riskHandler.CheckRisk)
		v1.GET("/risk/events", riskHandler.GetRiskEvents)
	}

	addr := fmt.Sprintf(":%d", cfg.App.Port)
	logger.Info("starting server", "addr", addr)
	go func() {
		if err := router.Run(addr); err != nil {
			logger.Error("server error", "error", err)
		}
	}()

	quit := make(chan os.Signal, 1)
	signal.Notify(quit, syscall.SIGINT, syscall.SIGTERM)
	<-quit
	logger.Info("shutting down service")
}
