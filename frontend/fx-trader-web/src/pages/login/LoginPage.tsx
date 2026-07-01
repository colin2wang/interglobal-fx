import { useState } from 'react';
import { Form, Input, Button, Card } from 'antd';
import { UserOutlined, LockOutlined } from '@ant-design/icons';
import { useAuth } from '@/composables/useAuth';

export const LoginPage = () => {
  const [loading, setLoading] = useState(false);
  const { login } = useAuth();
  const handleSubmit = async (values: { username: string; password: string }) => {
    setLoading(true);
    try { await login(values.username, values.password); } finally { setLoading(false); }
  };
  return (
    <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '100vh', background: 'linear-gradient(135deg, #667eea 0%, #764ba2 100%)' }}>
      <Card style={{ width: 400 }} title="FX Trading Terminal">
        <Form onFinish={handleSubmit} size="large">
          <Form.Item name="username" rules={[{ required: true, message: 'Please enter username' }]}><Input prefix={<UserOutlined />} placeholder="Username" /></Form.Item>
          <Form.Item name="password" rules={[{ required: true, message: 'Please enter password' }]}><Input.Password prefix={<LockOutlined />} placeholder="Password" /></Form.Item>
          <Form.Item><Button type="primary" htmlType="submit" loading={loading} block>Login</Button></Form.Item>
        </Form>
      </Card>
    </div>
  );
};
