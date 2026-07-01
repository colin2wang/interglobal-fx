package model

type Quote struct {
	Symbol    string  `json:"symbol"`
	Bid       float64 `json:"bid"`
	Ask       float64 `json:"ask"`
	BidSize   float64 `json:"bid_size"`
	AskSize   float64 `json:"ask_size"`
	Spread    float64 `json:"spread"`
	Timestamp int64   `json:"timestamp"`
	Source    string  `json:"source"`
}

type QuoteResponse struct {
	Code int    `json:"code"`
	Msg  string `json:"msg"`
	Data *Quote `json:"data,omitempty"`
}

type QuoteListResponse struct {
	Code int      `json:"code"`
	Msg  string   `json:"msg"`
	Data []*Quote `json:"data,omitempty"`
}
