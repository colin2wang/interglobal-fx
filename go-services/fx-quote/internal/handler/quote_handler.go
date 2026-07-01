package handler

import (
	"net/http"

	"github.com/gin-gonic/gin"

	"fx-quote/internal/model"
	"fx-quote/internal/service"
)

type QuoteHandler struct {
	quoteService *service.QuoteService
}

func NewQuoteHandler(quoteService *service.QuoteService) *QuoteHandler {
	return &QuoteHandler{quoteService: quoteService}
}

func (h *QuoteHandler) GetQuote(c *gin.Context) {
	symbol := c.Param("symbol")
	if symbol == "" {
		c.JSON(http.StatusBadRequest, model.QuoteResponse{Code: 400, Msg: "symbol is required"})
		return
	}
	quote, err := h.quoteService.GetQuote(symbol)
	if err != nil {
		c.JSON(http.StatusInternalServerError, model.QuoteResponse{Code: 500, Msg: err.Error()})
		return
	}
	if quote == nil {
		c.JSON(http.StatusNotFound, model.QuoteResponse{Code: 404, Msg: "quote not found"})
		return
	}
	c.JSON(http.StatusOK, model.QuoteResponse{Code: 0, Msg: "ok", Data: quote})
}

func (h *QuoteHandler) GetQuotes(c *gin.Context) {
	quotes, err := h.quoteService.GetQuotes()
	if err != nil {
		c.JSON(http.StatusInternalServerError, model.QuoteListResponse{Code: 500, Msg: err.Error()})
		return
	}
	c.JSON(http.StatusOK, model.QuoteListResponse{Code: 0, Msg: "ok", Data: quotes})
}
