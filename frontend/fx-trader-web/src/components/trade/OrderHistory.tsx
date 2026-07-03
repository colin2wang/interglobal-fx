import { useEffect } from 'react';
import { Table, Tag } from 'antd';
import type { ColumnsType } from 'antd/es/table';
import { useOrderStore } from '@/store/orderStore';
import { orderApi } from '@/api';
import { formatDate } from '@/utils/format';

export const OrderHistory = () => {
  const { orderHistory, setOrderHistory } = useOrderStore();

  useEffect(() => {
    orderApi
      .getOrderHistory({ page: 1, pageSize: 50 })
      .then((res) => setOrderHistory(res.list))
      .catch(() => {});
  }, []);

  const columns: ColumnsType<any> = [
    { title: 'Symbol', dataIndex: 'symbol' },
    {
      title: 'Side',
      dataIndex: 'side',
      render: (s: string) => <Tag color={s === 'buy' ? 'green' : 'red'}>{s.toUpperCase()}</Tag>,
    },
    { title: 'Type', dataIndex: 'type' },
    { title: 'Volume', dataIndex: 'volume' },
    { title: 'Price', dataIndex: 'price', render: (p: number) => p?.toFixed(5) || 'Market' },
    {
      title: 'P/L',
      dataIndex: 'profit',
      render: (p: number) => (
        <span style={{ color: p >= 0 ? '#52c41a' : '#ff4d4f' }}>{p?.toFixed(2)}</span>
      ),
    },
    { title: 'Time', dataIndex: 'openTime', render: formatDate },
  ];

  return (
    <Table
      columns={columns}
      dataSource={orderHistory}
      rowKey="id"
      size="small"
      pagination={{ pageSize: 20 }}
    />
  );
};
