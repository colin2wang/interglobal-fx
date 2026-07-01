import { Tag } from 'antd';

interface Props { value: number; prefix?: string; showSign?: boolean; }

export const ProfitLossBadge = ({ value, prefix = '', showSign = true }: Props) => {
  const isPositive = value >= 0;
  return <Tag color={isPositive ? 'green' : 'red'}>{prefix}{showSign && value > 0 ? '+' : ''}{value.toFixed(2)}</Tag>;
};
