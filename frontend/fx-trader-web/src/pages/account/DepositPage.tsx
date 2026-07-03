import { useState } from 'react';
import { Card, Form, InputNumber, Select, Button, Input, message } from 'antd';
import { accountApi } from '@/api';
import { useNavigate } from 'react-router-dom';

export const DepositPage = () => {
  const [form] = Form.useForm();
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();
  const handleSubmit = async (values: any) => {
    setLoading(true);
    try {
      await accountApi.deposit(values);
      message.success('Deposit submitted');
      navigate('/account');
    } catch {
      message.error('Failed');
    } finally {
      setLoading(false);
    }
  };
  return (
    <Card title="Deposit" style={{ maxWidth: 600, margin: '0 auto' }}>
      <Form form={form} layout="vertical" onFinish={handleSubmit}>
        <Form.Item name="amount" label="Amount" rules={[{ required: true }]}>
          <InputNumber min={1} style={{ width: '100%' }} prefix="$" />
        </Form.Item>
        <Form.Item name="method" label="Method" rules={[{ required: true }]}>
          <Select>
            <Select.Option value="bank">Bank Transfer</Select.Option>
            <Select.Option value="crypto">Cryptocurrency</Select.Option>
            <Select.Option value="ewallet">E-Wallet</Select.Option>
          </Select>
        </Form.Item>
        <Form.Item name="remark" label="Remark">
          <Input.TextArea rows={3} />
        </Form.Item>
        <Form.Item>
          <Button type="primary" htmlType="submit" loading={loading} block>
            Submit Deposit
          </Button>
        </Form.Item>
      </Form>
    </Card>
  );
};
