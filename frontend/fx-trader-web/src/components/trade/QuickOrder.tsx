import { Button, Space, Typography, Divider, message } from 'antd';
import { useQuoteStore } from '@/store/quoteStore';
import { orderApi } from '@/api';

export const QuickOrder = () => {
  const { selectedSymbol, quotes } = useQuoteStore();
  const quote = quotes[selectedSymbol];

  const quickTrade = async (side: 'buy' | 'sell', volume: number) => {
    try {
      await orderApi.createOrder({ symbol: selectedSymbol, side, type: 'market', volume });
      message.success(`${side === 'buy' ? 'Bought' : 'Sold'} ${volume} lots`);
    } catch {
      message.error('Order failed');
    }
  };

  return (
    <div>
      <Typography.Title level={5}>Quick Trade</Typography.Title>
      <div style={{ display: 'flex', gap: 8, marginBottom: 16 }}>
        <Button type="primary" danger block onClick={() => quickTrade('sell', 0.01)}>
          SELL {quote?.bid.toFixed(5)}
        </Button>
        <Button type="primary" block onClick={() => quickTrade('buy', 0.01)}>
          BUY {quote?.ask.toFixed(5)}
        </Button>
      </div>
      <Divider>Volumes</Divider>
      <Space wrap>
        {[0.01, 0.05, 0.1, 0.5, 1].map((v) => (
          <Button key={v} size="small" onClick={() => quickTrade('buy', v)}>
            {v}
          </Button>
        ))}
      </Space>
    </div>
  );
};
