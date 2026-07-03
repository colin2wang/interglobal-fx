import { Layout, Menu } from 'antd';
import {
  LineChartOutlined,
  OrderedListOutlined,
  AccountBookOutlined,
  UserOutlined,
} from '@ant-design/icons';
import { useNavigate, useLocation } from 'react-router-dom';

const { Sider } = Layout;
const items = [
  { key: '/trading', icon: <LineChartOutlined />, label: 'Trading' },
  { key: '/positions', icon: <OrderedListOutlined />, label: 'Positions' },
  { key: '/orders', icon: <OrderedListOutlined />, label: 'Orders' },
  { key: '/account', icon: <AccountBookOutlined />, label: 'Account' },
  { key: '/profile', icon: <UserOutlined />, label: 'Profile' },
];

export const Sidebar = () => {
  const navigate = useNavigate();
  const location = useLocation();
  return (
    <Sider width={200} style={{ background: '#fff', borderRight: '1px solid #f0f0f0' }}>
      <div
        style={{
          height: 64,
          display: 'flex',
          alignItems: 'center',
          justifyContent: 'center',
          fontWeight: 700,
          fontSize: 18,
          borderBottom: '1px solid #f0f0f0',
        }}
      >
        FX Trader
      </div>
      <Menu
        mode="inline"
        selectedKeys={[location.pathname]}
        items={items}
        onClick={({ key }) => navigate(key)}
        style={{ borderRight: 0 }}
      />
    </Sider>
  );
};
