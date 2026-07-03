import { Layout, Space, Avatar, Dropdown, Typography } from 'antd';
import { UserOutlined, SettingOutlined, LogoutOutlined } from '@ant-design/icons';
import { useUserStore } from '@/store/userStore';
import { useNavigate } from 'react-router-dom';

const { Header: AntHeader } = Layout;

export const Header = () => {
  const { userInfo, logout } = useUserStore();
  const navigate = useNavigate();
  const items = [
    {
      key: 'profile',
      icon: <UserOutlined />,
      label: 'Profile',
      onClick: () => navigate('/profile'),
    },
    { key: 'settings', icon: <SettingOutlined />, label: 'Settings' },
    { type: 'divider' as const },
    { key: 'logout', icon: <LogoutOutlined />, label: 'Logout', onClick: logout },
  ];
  return (
    <AntHeader
      style={{
        background: '#fff',
        padding: '0 24px',
        display: 'flex',
        justifyContent: 'flex-end',
        alignItems: 'center',
        borderBottom: '1px solid #f0f0f0',
      }}
    >
      <Dropdown menu={{ items }} placement="bottomRight">
        <Space style={{ cursor: 'pointer' }}>
          <Avatar icon={<UserOutlined />} />
          <Typography.Text>{userInfo?.nickname || 'User'}</Typography.Text>
        </Space>
      </Dropdown>
    </AntHeader>
  );
};
