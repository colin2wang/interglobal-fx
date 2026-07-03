import { useEffect, useState } from 'react';
import { Card, List, Badge, Typography, Empty, Tag } from 'antd';
import { BellOutlined, WarningOutlined, CheckCircleOutlined } from '@ant-design/icons';

const { Title, Text, Paragraph } = Typography;

interface Message {
  id: string;
  type: 'info' | 'warning' | 'success';
  title: string;
  content: string;
  time: string;
  read: boolean;
}

export function MessagesPage() {
  const [loading, setLoading] = useState(false);
  const [messages, setMessages] = useState<Message[]>([]);

  useEffect(() => {
    setLoading(true);
    setTimeout(() => {
      setMessages([
        { id: '1', type: 'success', title: 'Deposit Confirmed', content: 'Your deposit of $10,000 has been confirmed and credited to your account.', time: '2 hours ago', read: false },
        { id: '2', type: 'warning', title: 'Margin Warning', content: 'Your account margin level is below 100%. Please add funds or close positions.', time: '5 hours ago', read: false },
        { id: '3', type: 'info', title: 'System Maintenance', content: 'Scheduled maintenance on Jan 20, 2024 from 02:00-04:00 UTC.', time: '1 day ago', read: true },
        { id: '4', type: 'success', title: 'Withdrawal Processed', content: 'Your withdrawal of $5,000 has been processed. Funds will arrive in 1-3 business days.', time: '2 days ago', read: true },
      ]);
      setLoading(false);
    }, 500);
  }, []);

  const iconMap = {
    info: <BellOutlined style={{ color: '#1890ff' }} />,
    warning: <WarningOutlined style={{ color: '#faad14' }} />,
    success: <CheckCircleOutlined style={{ color: '#52c41a' }} />,
  };

  return (
    <Card>
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: 16 }}>
        <Title level={4} style={{ margin: 0 }}>Messages</Title>
        <Badge count={messages.filter(m => !m.read).length} />
      </div>
      {messages.length === 0 ? (
        <Empty description="No messages" />
      ) : (
        <List
          loading={loading}
          dataSource={messages}
          renderItem={item => (
            <List.Item style={{ background: item.read ? '#fafafa' : '#fff', padding: '12px 16px', borderRadius: 8, marginBottom: 8 }}>
              <List.Item.Meta
                avatar={iconMap[item.type]}
                title={<span>{item.title} {!item.read && <Tag color="blue">New</Tag>}</span>}
                description={<><Paragraph style={{ margin: 0 }}>{item.content}</Paragraph><Text type="secondary" style={{ fontSize: 12 }}>{item.time}</Text></>}
              />
            </List.Item>
          )}
        />
      )}
    </Card>
  );
}
