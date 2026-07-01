package handler

import (
	"net/http"
	"strconv"

	"github.com/gin-gonic/gin"

	"fx-quote/internal/model"
	"fx-quote/internal/service"
)

type KlineHandler struct {
	klineService *service.KlineService
}

func NewKlineHandler(klineService *service.KlineService) *KlineHandler {
	return &KlineHandler{klineService: klineService}
}

func (h *KlineHandler) GetKlines(c *gin.Context) {
	symbol := c.Param("symbol")
	if symbol == "" {
		c.JSON(http.StatusBadRequest, model.KlineResponse{Code: 400, Msg: "symbol is required"})
		return
	}
	period := c.DefaultQuery("period", "1m")
	limitStr := c.DefaultQuery("limit", "100")
	limit, err := strconv.Atoi(limitStr)
	if err != nil {
		limit = 100
	}
	klines, err := h.klineService.GetKlines(symbol, period, limit)
	if err != nil {
		c.JSON(http.StatusInternalServerError, model.KlineResponse{Code: 500, Msg: err.Error()})
		return
	}
	c.JSON(http.StatusOK, model.KlineResponse{Code: 0, Msg: "ok", Data: klines})
}
