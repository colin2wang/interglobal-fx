package handler

import (
	"net/http"
	"strconv"

	"github.com/gin-gonic/gin"

	"fx-risk/internal/model"
	"fx-risk/internal/service"
)

type RiskHandler struct {
	riskService *service.RiskService
}

func NewRiskHandler(riskService *service.RiskService) *RiskHandler {
	return &RiskHandler{riskService: riskService}
}

func (h *RiskHandler) CheckRisk(c *gin.Context) {
	var req model.RiskCheckRequest
	if err := c.ShouldBindJSON(&req); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"code": 400, "msg": "invalid request: " + err.Error()})
		return
	}
	resp := h.riskService.CheckRisk(&req)
	c.JSON(http.StatusOK, resp)
}

func (h *RiskHandler) GetRiskEvents(c *gin.Context) {
	accountID := c.Query("account_id")
	limitStr := c.DefaultQuery("limit", "100")
	limit, err := strconv.Atoi(limitStr)
	if err != nil {
		limit = 100
	}
	events, err := h.riskService.GetRiskEvents(accountID, limit)
	if err != nil {
		c.JSON(http.StatusInternalServerError, model.RiskEventListResponse{Code: 500, Msg: err.Error()})
		return
	}
	c.JSON(http.StatusOK, model.RiskEventListResponse{Code: 0, Msg: "ok", Data: events})
}
