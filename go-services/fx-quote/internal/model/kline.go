package model

type Kline struct {
	Symbol    string  `json:"symbol"`
	Period    string  `json:"period"`
	Open      float64 `json:"open"`
	High      float64 `json:"high"`
	Low       float64 `json:"low"`
	Close     float64 `json:"close"`
	Volume    float64 `json:"volume"`
	Timestamp int64   `json:"timestamp"`
}

type KlineResponse struct {
	Code int      `json:"code"`
	Msg  string   `json:"msg"`
	Data []*Kline `json:"data,omitempty"`
}
