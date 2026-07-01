package logger

import (
	"log/slog"
	"os"
)

var defaultLogger *slog.Logger

func Init(level string) {
	var lvl slog.Level
	switch level {
	case "debug":
		lvl = slog.LevelDebug
	case "warn":
		lvl = slog.LevelWarn
	case "error":
		lvl = slog.LevelError
	default:
		lvl = slog.LevelInfo
	}
	opts := &slog.HandlerOptions{Level: lvl}
	defaultLogger = slog.New(slog.NewJSONHandler(os.Stdout, opts))
}

func Info(msg string, args ...any) {
	if defaultLogger != nil {
		defaultLogger.Info(msg, args...)
	}
}

func Error(msg string, args ...any) {
	if defaultLogger != nil {
		defaultLogger.Error(msg, args...)
	}
}

func Debug(msg string, args ...any) {
	if defaultLogger != nil {
		defaultLogger.Debug(msg, args...)
	}
}

func With(args ...any) *slog.Logger {
	if defaultLogger != nil {
		return defaultLogger.With(args...)
	}
	return slog.Default()
}
