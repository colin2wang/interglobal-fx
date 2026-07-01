import dayjs from 'dayjs';

export const formatMoney = (value: number, currency = 'USD', decimals = 2): string => {
  return new Intl.NumberFormat('en-US', { style: 'currency', currency, minimumFractionDigits: decimals, maximumFractionDigits: decimals }).format(value);
};

export const formatDate = (date: string | number | Date, format = 'YYYY-MM-DD HH:mm:ss'): string => {
  return dayjs(date).format(format);
};

export const formatNumber = (value: number, decimals = 2): string => {
  return new Intl.NumberFormat('en-US', { minimumFractionDigits: decimals, maximumFractionDigits: decimals }).format(value);
};

export const formatPercent = (value: number, decimals = 2): string => {
  return `${value >= 0 ? '+' : ''}${(value * 100).toFixed(decimals)}%`;
};

export const formatVolume = (value: number): string => {
  if (value >= 1000000) return `${(value / 1000000).toFixed(2)}M`;
  if (value >= 1000) return `${(value / 1000).toFixed(2)}K`;
  return value.toFixed(2);
};
