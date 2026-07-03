import { useEffect, useState } from 'react';
import { Table, Card, DatePicker, Select, Space, Typography } from 'antd';
import type { ColumnsType } from 'antd/es/table';
import dayjs from 'dayjs';

const { RangePicker } = DatePicker;
const { Title } = Typography;

interface Transaction {
  id: string;
  type: string;
  amount: string;
  currency: string;
  status: string;
  createTime: string;
  description: string;
}

const columns: ColumnsType<Transaction> = [
  { title: 'Time', dataIndex: 'createTime', key: 'createTime' },
  { title: 'Type', dataIndex: 'type', key: 'type', render: (type: string) => {
    const colorMap: Record<string, string> = { deposit: '#52c41a', withdrawal: '#ff4d4f', trade: '#1890ff', swap: '#722ed1', commission: '#fa8c16' };
    return <span style={{ color: colorMap[type] || '#333' }}>{type}</span>;
  }},
  { title: 'Amount', dataIndex: 'amount', key: 'amount', render: (amount: string, record: string) => `${record.currency} ${amount}` },
  { title: 'Status', dataIndex: 'status', key: 'status', render: (status: string) => {
    const colorMap: Record<string, string> = { completed: 'green', pending: 'orange', failed: 'red' };
    return <span style={{ color: colorMap[status] || '#333' }}>{status}</span>;
  }},
  { title: 'Description', dataIndex: 'description', key: 'description' },
];

export function HistoryPage() {
  const [loading, setLoading] = useState(false);
  const [data, setData] = useState<Transaction[]>([]);

  useEffect(() => {
    setLoading(true);
    setTimeout(() => {
      setData([
        { id: '1', type: 'deposit', amount: '10,000', currency: 'USD', status: 'completed', createTime: '2024-01-15 10:30', description: 'Bank wire deposit' },
        { id: '2', type: 'trade', amount: '-250', currency: 'USD', status: 'completed', createTime: '2024-01-15 14:22', description: 'EURUSD Buy 0.1 lot' },
        { id: '3', type: 'swap', amount: '-12.5', currency: 'USD', status: 'completed', createTime: '2024-01-16 00:00', description: 'Overnight swap EURUSD' },
        { id: '4', type: 'withdrawal', amount: '-5,000', currency: 'USD', status: 'pending', createTime: '2024-01-16 09:15', description: 'Bank wire withdrawal' },
      ]);
      setLoading(false);
    }, 500);
  }, []);

  return (
    <Card>
      <Title level={4}>Fund History</Title>
      <Space style={{ marginBottom: 16 }}>
        <RangePicker />
        <Select placeholder="Type" style={{ width: 120 }} allowClear>
          <Select.Option value="deposit">Deposit</Select.Option>
          <Select.Option value="withdrawal">Withdrawal</Select.Option>
          <Select.Option value="trade">Trade</Select.Option>
          <Select.Option value="swap">Swap</Select.Option>
        </Select>
      </Space>
      <Table columns={columns} dataSource={data} loading={loading} rowKey="id" pagination={{ pageSize: 20 }} />
    </Card>
  );
}
