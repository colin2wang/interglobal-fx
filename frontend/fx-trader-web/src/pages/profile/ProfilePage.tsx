import { Card, Form, Input, Button, Avatar, message } from 'antd';
import { UserOutlined } from '@ant-design/icons';
import { useUserStore } from '@/store/userStore';

export const ProfilePage = () => {
  const { userInfo } = useUserStore();
  const [form] = Form.useForm();
  return (
    <Card title="Profile" style={{ maxWidth: 600, margin: '0 auto' }}>
      <div style={{ textAlign: 'center', marginBottom: 24 }}>
        <Avatar size={100} icon={<UserOutlined />} />
      </div>
      <Form
        form={form}
        layout="vertical"
        initialValues={userInfo ?? undefined}
        onFinish={() => message.success('Profile updated')}
      >
        <Form.Item name="nickname" label="Nickname">
          <Input />
        </Form.Item>
        <Form.Item name="email" label="Email">
          <Input />
        </Form.Item>
        <Form.Item name="phone" label="Phone">
          <Input />
        </Form.Item>
        <Form.Item>
          <Button type="primary" htmlType="submit" block>
            Update Profile
          </Button>
        </Form.Item>
      </Form>
    </Card>
  );
};
