import { Tag, Typography } from 'antd';
import { ArrowUpOutlined, ArrowDownOutlined } from '@ant-design/icons';
import type { SymbolQuote } from '@/api/quote';

interface Props { quote: SymbolQuote; onClick?: () => void; selected?: boolean; }

export const SymbolTicker = ({ quote, onClick, selected }: Props) => {
  const isPositive = quote.changePercent24h >= 0;
  return (
    <div onClick={onClick} style={{ padding: 12, borderBottom: '1px solid #f0f0f0', cursor: 'pointer', backgroundColor: selected ? '#e6f7ff' : 'transparent' }}>
      <div style={{ display: 'flex', justifyContent: 'space-between', marginBottom: 4 }}>
        <Typography.Text strong>{quote.symbol}</Typography.Text>
        <Tag color={isPositive ? 'green' : 'red'}>{isPositive ? <ArrowUpOutlined /> : <ArrowDownOutlined />}{Math.abs(quote.changePercent24h).toFixed(2)}%</Tag>
      </div>
      <div style={{ display: 'flex', justifyContent: 'space-between' }}>
        <span>
          <span style={{ color: '#26a69a', fontWeight: 600 }}>{quote.bid.toFixed(5)}</span>
          <span style={{ margin: '0 8px', color: '#999' }}>/</span>
          <span style={{ color: '#ef5350', fontWeight: 600 }}>{quote.ask.toFixed(5)}</span>
        </span>
        <Typography.Text type="secondary">{quote.spread.toFixed(1)}</Typography.Text>
      </div>
    </div>
  );
};
