import { useState } from 'react';
import { Form, InputNumber, Select, Button, Radio, Divider, message } from 'antd';
import { useQuoteStore } from '@/store/quoteStore';
import { orderApi } from '@/api';

export const OrderForm = () => {
  const [form] = Form.useForm();
  const [loading, setLoading] = useState(false);
  const { selectedSymbol } = useQuoteStore();

  const handleSubmit = async (values: any) => {
    setLoading(true);
    try {
      await orderApi.createOrder({ symbol: selectedSymbol, ...values });
      message.success('Order placed');
      form.resetFields();
    } catch {
      message.error('Failed');
    } finally {
      setLoading(false);
    }
  };

  return (
    <Form
      form={form}
      layout="vertical"
      onFinish={handleSubmit}
      initialValues={{ side: 'buy', type: 'market', volume: 0.01 }}
    >
      <Form.Item name="side" label="Side">
        <Radio.Group buttonStyle="solid" style={{ width: '100%' }}>
          <Radio.Button value="buy" style={{ width: '50%', textAlign: 'center' }}>
            Buy
          </Radio.Button>
          <Radio.Button value="sell" style={{ width: '50%', textAlign: 'center' }}>
            Sell
          </Radio.Button>
        </Radio.Group>
      </Form.Item>
      <Form.Item name="type" label="Order Type">
        <Select>
          <Select.Option value="market">Market</Select.Option>
          <Select.Option value="limit">Limit</Select.Option>
          <Select.Option value="stop">Stop</Select.Option>
        </Select>
      </Form.Item>
      <Form.Item name="volume" label="Volume (Lots)">
        <InputNumber min={0.01} step={0.01} style={{ width: '100%' }} />
      </Form.Item>
      <Form.Item noStyle shouldUpdate={(p, c) => p.type !== c.type}>
        {({ getFieldValue }) =>
          getFieldValue('type') !== 'market' ? (
            <Form.Item name="price" label="Price">
              <InputNumber style={{ width: '100%' }} step={0.00001} />
            </Form.Item>
          ) : null
        }
      </Form.Item>
      <Divider>TP/SL</Divider>
      <Form.Item name="takeProfit" label="Take Profit">
        <InputNumber style={{ width: '100%' }} step={0.00001} />
      </Form.Item>
      <Form.Item name="stopLoss" label="Stop Loss">
        <InputNumber style={{ width: '100%' }} step={0.00001} />
      </Form.Item>
      <Form.Item>
        <Button type="primary" htmlType="submit" loading={loading} block size="large">
          Place Order
        </Button>
      </Form.Item>
    </Form>
  );
};
