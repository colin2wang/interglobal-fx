package config

import (
	"os"

	"gopkg.in/yaml.v3"
)

type Config struct {
	App    AppConfig    `yaml:"app"`
	FIX    FIXConfig    `yaml:"fix"`
	Logging LoggingConfig `yaml:"logging"`
}

type AppConfig struct {
	Name string `yaml:"name"`
	Port int    `yaml:"port"`
	Mode string `yaml:"mode"`
}

type FIXConfig struct {
	DefaultVersion      string `yaml:"default_version"`
	HeartbeatInterval   int    `yaml:"heartbeat_interval"`
	SessionStorePath    string `yaml:"session_store_path"`
	ListenAddr          string `yaml:"listen_addr"`
}

type LoggingConfig struct {
	Level string `yaml:"level"`
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
