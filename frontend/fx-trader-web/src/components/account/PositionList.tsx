import { useEffect } from 'react';
import { Table, Tag, Button, Popconfirm, message } from 'antd';
import type { ColumnsType } from 'antd/es/table';
import { usePositionStore } from '@/store/positionStore';
import { positionApi } from '@/api';
import { formatDate } from '@/utils/format';

export const PositionList = () => {
  const { positions, setPositions, removePosition } = usePositionStore();

  useEffect(() => {
    positionApi
      .getPositions()
      .then((data) => setPositions(data))
      .catch(() => {});
  }, []);

  const handleClose = async (id: string) => {
    try {
      await positionApi.closePosition(id);
      removePosition(id);
      message.success('Closed');
    } catch {
      message.error('Failed');
    }
  };

  const columns: ColumnsType<any> = [
    { title: 'Symbol', dataIndex: 'symbol' },
    {
      title: 'Side',
      dataIndex: 'side',
      render: (s: string) => <Tag color={s === 'buy' ? 'green' : 'red'}>{s.toUpperCase()}</Tag>,
    },
    { title: 'Volume', dataIndex: 'volume' },
    { title: 'Open', dataIndex: 'openPrice', render: (p: number) => p.toFixed(5) },
    { title: 'Current', dataIndex: 'currentPrice', render: (p: number) => p.toFixed(5) },
    {
      title: 'P/L',
      dataIndex: 'profit',
      render: (p: number) => (
        <span style={{ color: p >= 0 ? '#52c41a' : '#ff4d4f', fontWeight: 600 }}>
          {p >= 0 ? '+' : ''}
          {p.toFixed(2)}
        </span>
      ),
    },
    { title: 'Time', dataIndex: 'openTime', render: formatDate },
    {
      title: 'Action',
      key: 'action',
      render: (_, r) => (
        <Popconfirm title="Close?" onConfirm={() => handleClose(r.id)}>
          <Button type="link" danger size="small">
            Close
          </Button>
        </Popconfirm>
      ),
    },
  ];

  return (
    <Table columns={columns} dataSource={positions} rowKey="id" size="small" pagination={false} />
  );
};
