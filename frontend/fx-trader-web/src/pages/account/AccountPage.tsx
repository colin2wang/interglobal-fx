import { Card, Button, Space } from 'antd';
import { useNavigate } from 'react-router-dom';
import { AccountBalance } from '@/components/account/AccountBalance';

export const AccountPage = () => {
  const navigate = useNavigate();
  return (
    <div>
      <AccountBalance />
      <Card style={{ marginTop: 16 }}>
        <Space>
          <Button type="primary" onClick={() => navigate('/account/deposit')}>Deposit</Button>
          <Button onClick={() => navigate('/account/withdraw')}>Withdraw</Button>
        </Space>
      </Card>
    </div>
  );
};
