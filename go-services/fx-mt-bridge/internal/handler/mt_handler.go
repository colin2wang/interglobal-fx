package handler

import (
	"net/http"
	"strconv"

	"github.com/gin-gonic/gin"

	"fx-mt-bridge/internal/service"
)

type MTHandler struct {
	mtService *service.MTService
}

func NewMTHandler(mtService *service.MTService) *MTHandler {
	return &MTHandler{mtService: mtService}
}

type OpenOrderRequest struct {
	Login     int64   `json:"login"`
	Symbol    string  `json:"symbol"`
	Type      string  `json:"type"`
	Volume    float64 `json:"volume"`
	Price     float64 `json:"price"`
	StopLoss  float64 `json:"stop_loss"`
	TakeProfit float64 `json:"take_profit"`
	Comment   string  `json:"comment"`
}

func (h *MTHandler) GetStatus(c *gin.Context) {
	c.JSON(http.StatusOK, gin.H{
		"code":      0,
		"msg":       "ok",
		"connected": h.mtService.IsConnected(),
	})
}

func (h *MTHandler) GetAccount(c *gin.Context) {
	loginStr := c.Param("login")
	login, err := strconv.ParseInt(loginStr, 10, 64)
	if err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"code": 400, "msg": "invalid login"})
		return
	}
	info, err := h.mtService.GetAccountInfo(login)
	if err != nil {
		c.JSON(http.StatusInternalServerError, gin.H{"code": 500, "msg": err.Error()})
		return
	}
	c.JSON(http.StatusOK, gin.H{"code": 0, "msg": "ok", "data": info})
}

func (h *MTHandler) OpenOrder(c *gin.Context) {
	var req OpenOrderRequest
	if err := c.ShouldBindJSON(&req); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"code": 400, "msg": "invalid request: " + err.Error()})
		return
	}
	order, err := h.mtService.OpenOrder(
		req.Login, req.Symbol, req.Type,
		req.Volume, req.Price, req.StopLoss, req.TakeProfit, req.Comment,
	)
	if err != nil {
		c.JSON(http.StatusInternalServerError, gin.H{"code": 500, "msg": err.Error()})
		return
	}
	c.JSON(http.StatusOK, gin.H{"code": 0, "msg": "ok", "data": order})
}

func (h *MTHandler) CloseOrder(c *gin.Context) {
	orderIDStr := c.Param("order_id")
	orderID, err := strconv.ParseInt(orderIDStr, 10, 64)
	if err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"code": 400, "msg": "invalid order_id"})
		return
	}
	if err := h.mtService.CloseOrder(orderID); err != nil {
		c.JSON(http.StatusInternalServerError, gin.H{"code": 500, "msg": err.Error()})
		return
	}
	c.JSON(http.StatusOK, gin.H{"code": 0, "msg": "ok"})
}

func (h *MTHandler) GetOrders(c *gin.Context) {
	loginStr := c.Param("login")
	login, err := strconv.ParseInt(loginStr, 10, 64)
	if err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"code": 400, "msg": "invalid login"})
		return
	}
	orders := h.mtService.GetOrders(login)
	c.JSON(http.StatusOK, gin.H{"code": 0, "msg": "ok", "data": orders})
}

func (h *MTHandler) GetPositions(c *gin.Context) {
	loginStr := c.Param("login")
	login, err := strconv.ParseInt(loginStr, 10, 64)
	if err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"code": 400, "msg": "invalid login"})
		return
	}
	positions, err := h.mtService.GetPositions(login)
	if err != nil {
		c.JSON(http.StatusInternalServerError, gin.H{"code": 500, "msg": err.Error()})
		return
	}
	c.JSON(http.StatusOK, gin.H{"code": 0, "msg": "ok", "data": positions})
}
