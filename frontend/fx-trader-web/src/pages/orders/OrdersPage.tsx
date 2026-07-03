import { Tabs, Card } from 'antd';
import { OrderHistory } from '@/components/trade/OrderHistory';

export const OrdersPage = () => (
  <Card>
    <Tabs
      items={[
        { key: 'pending', label: 'Pending Orders', children: <OrderHistory /> },
        { key: 'history', label: 'Order History', children: <OrderHistory /> },
      ]}
    />
  </Card>
);
