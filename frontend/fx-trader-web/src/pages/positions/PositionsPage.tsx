import { Card, Row, Col, Statistic } from 'antd';
import { PositionList } from '@/components/account/PositionList';
import { usePositionStore } from '@/store/positionStore';

export const PositionsPage = () => {
  const { totalProfit, totalMargin } = usePositionStore();
  return (
    <div>
      <Row gutter={16} style={{ marginBottom: 16 }}>
        <Col span={8}>
          <Card>
            <Statistic
              title="Total P/L"
              value={totalProfit}
              precision={2}
              prefix="$"
              valueStyle={{ color: totalProfit >= 0 ? '#3f8600' : '#cf1322' }}
            />
          </Card>
        </Col>
        <Col span={8}>
          <Card>
            <Statistic title="Total Margin" value={totalMargin} precision={2} prefix="$" />
          </Card>
        </Col>
      </Row>
      <Card title="Open Positions">
        <PositionList />
      </Card>
    </div>
  );
};
