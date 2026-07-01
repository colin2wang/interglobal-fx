package handler

import (
	"net/http"

	"github.com/gin-gonic/gin"

	"fx-fix-gateway/internal/service"
)

type FixHandler struct {
	fixService *service.FixService
}

func NewFixHandler(fixService *service.FixService) *FixHandler {
	return &FixHandler{fixService: fixService}
}

type NewOrderRequest struct {
	SenderCompID string  `json:"sender_comp_id"`
	TargetCompID string  `json:"target_comp_id"`
	ClOrdID      string  `json:"cl_ord_id"`
	Symbol       string  `json:"symbol"`
	Side         string  `json:"side"`
	Volume       float64 `json:"volume"`
	Price        float64 `json:"price"`
}

type OrderCancelRequest struct {
	SenderCompID string `json:"sender_comp_id"`
	TargetCompID string `json:"target_comp_id"`
	OrderID      string `json:"order_id"`
	ClOrdID      string `json:"cl_ord_id"`
	Symbol       string `json:"symbol"`
	Side         string `json:"side"`
}

type MarketDataRequest struct {
	SenderCompID string   `json:"sender_comp_id"`
	TargetCompID string   `json:"target_comp_id"`
	ReqID        string   `json:"req_id"`
	Symbols      []string `json:"symbols"`
}

func (h *FixHandler) GetStatus(c *gin.Context) {
	c.JSON(http.StatusOK, gin.H{"code": 0, "msg": "ok"})
}

func (h *FixHandler) SendNewOrder(c *gin.Context) {
	var req NewOrderRequest
	if err := c.ShouldBindJSON(&req); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"code": 400, "msg": "invalid request: " + err.Error()})
		return
	}
	if err := h.fixService.SendNewOrder(
		req.SenderCompID, req.TargetCompID,
		req.ClOrdID, req.Symbol, req.Side,
		req.Volume, req.Price,
	); err != nil {
		c.JSON(http.StatusInternalServerError, gin.H{"code": 500, "msg": err.Error()})
		return
	}
	c.JSON(http.StatusOK, gin.H{"code": 0, "msg": "ok"})
}

func (h *FixHandler) SendOrderCancel(c *gin.Context) {
	var req OrderCancelRequest
	if err := c.ShouldBindJSON(&req); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"code": 400, "msg": "invalid request: " + err.Error()})
		return
	}
	if err := h.fixService.SendOrderCancel(
		req.SenderCompID, req.TargetCompID,
		req.OrderID, req.ClOrdID, req.Symbol, req.Side,
	); err != nil {
		c.JSON(http.StatusInternalServerError, gin.H{"code": 500, "msg": err.Error()})
		return
	}
	c.JSON(http.StatusOK, gin.H{"code": 0, "msg": "ok"})
}

func (h *FixHandler) SendMarketDataRequest(c *gin.Context) {
	var req MarketDataRequest
	if err := c.ShouldBindJSON(&req); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"code": 400, "msg": "invalid request: " + err.Error()})
		return
	}
	if err := h.fixService.SendMarketDataRequest(
		req.SenderCompID, req.TargetCompID,
		req.ReqID, req.Symbols,
	); err != nil {
		c.JSON(http.StatusInternalServerError, gin.H{"code": 500, "msg": err.Error()})
		return
	}
	c.JSON(http.StatusOK, gin.H{"code": 0, "msg": "ok"})
}

func (h *FixHandler) GetSession(c *gin.Context) {
	sender := c.Query("sender_comp_id")
	target := c.Query("target_comp_id")
	if sender == "" || target == "" {
		c.JSON(http.StatusBadRequest, gin.H{"code": 400, "msg": "sender_comp_id and target_comp_id required"})
		return
	}
	info, err := h.fixService.GetSessionInfo(sender, target)
	if err != nil {
		c.JSON(http.StatusNotFound, gin.H{"code": 404, "msg": err.Error()})
		return
	}
	c.JSON(http.StatusOK, gin.H{"code": 0, "msg": "ok", "data": info})
}
