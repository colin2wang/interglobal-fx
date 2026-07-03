import { Card, Row, Col, Statistic } from 'antd';
import { useAccountStore } from '@/store/accountStore';

export const AccountBalance = () => {
  const { accountInfo } = useAccountStore();
  if (!accountInfo) return null;
  return (
    <Card>
      <Row gutter={[24, 24]}>
        <Col span={6}>
          <Statistic title="Balance" value={accountInfo.balance} precision={2} prefix="$" />
        </Col>
        <Col span={6}>
          <Statistic title="Equity" value={accountInfo.equity} precision={2} prefix="$" />
        </Col>
        <Col span={6}>
          <Statistic title="Margin" value={accountInfo.margin} precision={2} prefix="$" />
        </Col>
        <Col span={6}>
          <Statistic title="Free Margin" value={accountInfo.freeMargin} precision={2} prefix="$" />
        </Col>
      </Row>
    </Card>
  );
};
