package config

import (
	"os"

	"gopkg.in/yaml.v3"
)

type Config struct {
	App   AppConfig   `yaml:"app"`
	DB    DBConfig    `yaml:"database"`
	Risk  RiskConfig  `yaml:"risk"`
}

type AppConfig struct {
	Name string `yaml:"name"`
	Port int    `yaml:"port"`
	Mode string `yaml:"mode"`
}

type DBConfig struct {
	Host     string `yaml:"host"`
	Port     int    `yaml:"port"`
	User     string `yaml:"user"`
	Password string `yaml:"password"`
	DBName   string `yaml:"dbname"`
}

type RiskConfig struct {
	MaxPositionSize       float64 `yaml:"max_position_size"`
	MaxOrderSize          float64 `yaml:"max_order_size"`
	MaxOpenOrders         int     `yaml:"max_open_orders"`
	MaxLeverage           float64 `yaml:"max_leverage"`
	MinMarginRatio        float64 `yaml:"min_margin_ratio"`
	FrequencyWindowSecs   int     `yaml:"frequency_window_seconds"`
	FrequencyMaxTrades    int     `yaml:"frequency_max_trades"`
}

func Load(path string) (*Config, error) {
	data, err := os.ReadFile(path)
	if err != nil {
		return nil, err
	}
	var cfg Config
	if err := yaml.Unmarshal(data, &cfg); err != nil {
		return nil, err
	}
	return &cfg, nil
}
