package main

import (
	"fmt"
	"log"
	"os"
	"os/signal"
	"syscall"

	"github.com/gin-gonic/gin"

	"fx-mt-bridge/internal/config"
	"fx-mt-bridge/internal/handler"
	"fx-mt-bridge/internal/service"
	"fx-mt-bridge/pkg/logger"
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

	mtService := service.NewMTService(cfg)
	if err := mtService.Connect(); err != nil {
		logger.Error("MT4 connection failed", "error", err)
	}
	defer mtService.Disconnect()

	mtHandler := handler.NewMTHandler(mtService)

	router := gin.New()
	router.Use(gin.Recovery())
	if cfg.App.Mode == "debug" {
		router.Use(gin.Logger())
	}

	v1 := router.Group("/api/v1")
	{
		v1.GET("/mt/status", mtHandler.GetStatus)
		v1.GET("/mt/account/:login", mtHandler.GetAccount)
		v1.POST("/mt/order", mtHandler.OpenOrder)
		v1.DELETE("/mt/order/:order_id", mtHandler.CloseOrder)
		v1.GET("/mt/orders/:login", mtHandler.GetOrders)
		v1.GET("/mt/positions/:login", mtHandler.GetPositions)
	}

	router.GET("/health", func(c *gin.Context) {
		status := "UP"
		if !mtService.IsConnected() {
			status = "DEGRADED"
		}
		c.JSON(200, gin.H{
			"status":  status,
			"service": cfg.App.Name,
			"checks": map[string]string{
				"mt4_connection": map[bool]string{true: "UP", false: "DOWN"}[mtService.IsConnected()],
			},
		})
	})

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
