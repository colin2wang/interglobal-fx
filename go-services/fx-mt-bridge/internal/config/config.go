package config

import (
	"os"

	"gopkg.in/yaml.v3"
)

type Config struct {
	App  AppConfig  `yaml:"app"`
	MT   MTConfig   `yaml:"mt"`
	Sync SyncConfig `yaml:"sync"`
}

type AppConfig struct {
	Name string `yaml:"name"`
	Port int    `yaml:"port"`
	Mode string `yaml:"mode"`
}

type MTConfig struct {
	Host           string `yaml:"host"`
	Port           int    `yaml:"port"`
	Login          string `yaml:"login"`
	Password       string `yaml:"password"`
	TimeoutSeconds int    `yaml:"timeout_seconds"`
}

type SyncConfig struct {
	AccountIntervalSecs  int `yaml:"account_interval_seconds"`
	OrderIntervalSecs    int `yaml:"order_interval_seconds"`
	PositionIntervalSecs int `yaml:"position_interval_seconds"`
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
