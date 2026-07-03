import { Row, Col, Card } from 'antd';
import { CandlestickChart } from '@/components/chart/CandlestickChart';
import { SymbolList } from '@/components/quote/SymbolList';
import { OrderForm } from '@/components/trade/OrderForm';
import { QuickOrder } from '@/components/trade/QuickOrder';
import { useQuoteStore } from '@/store/quoteStore';

export const TradingPage = () => {
  const { selectedSymbol } = useQuoteStore();
  return (
    <Row gutter={16}>
      <Col span={4}>
        <SymbolList />
      </Col>
      <Col span={14}>
        <Card title={selectedSymbol}>
          <CandlestickChart data={[]} />
        </Card>
      </Col>
      <Col span={6}>
        <Card title="Order" style={{ marginBottom: 16 }}>
          <OrderForm />
        </Card>
        <Card title="Quick Trade">
          <QuickOrder />
        </Card>
      </Col>
    </Row>
  );
};
