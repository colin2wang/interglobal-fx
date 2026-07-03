import { useState } from 'react';
import { Card, Steps, Form, Input, Upload, Button, message, Typography, Result } from 'antd';
import { UploadOutlined, IdCardOutlined, EnvironmentOutlined, UserOutlined } from '@ant-design/icons';

const { Title, Text } = Typography;
const { Step } = Steps;

export function KycPage() {
  const [current, setCurrent] = useState(0);
  const [form] = Form.useForm();

  const steps = [
    {
      title: 'Identity',
      icon: <IdCardOutlined />,
      content: (
        <Form form={form} layout="vertical" style={{ maxWidth: 500 }}>
          <Form.Item label="Full Name" name="fullName" rules={[{ required: true }]}>
            <Input prefix={<UserOutlined />} placeholder="As shown on ID" />
          </Form.Item>
          <Form.Item label="ID Type" name="idType" rules={[{ required: true }]}>
            <Input placeholder="Passport / ID Card / Driver's License" />
          </Form.Item>
          <Form.Item label="ID Number" name="idNumber" rules={[{ required: true }]}>
            <Input placeholder="ID document number" />
          </Form.Item>
          <Form.Item label="Front of ID" name="idFront">
            <Upload beforeUpload={() => false} maxCount={1}>
              <Button icon={<UploadOutlined />}>Upload Front</Button>
            </Upload>
          </Form.Item>
          <Form.Item label="Back of ID" name="idBack">
            <Upload beforeUpload={() => false} maxCount={1}>
              <Button icon={<UploadOutlined />}>Upload Back</Button>
            </Upload>
          </Form.Item>
        </Form>
      ),
    },
    {
      title: 'Address',
      icon: <EnvironmentOutlined />,
      content: (
        <Form layout="vertical" style={{ maxWidth: 500 }}>
          <Form.Item label="Address" name="address" rules={[{ required: true }]}>
            <Input placeholder="Street address" />
          </Form.Item>
          <Form.Item label="City" name="city" rules={[{ required: true }]}>
            <Input />
          </Form.Item>
          <Form.Item label="Country" name="country" rules={[{ required: true }]}>
            <Input />
          </Form.Item>
          <Form.Item label="Proof of Address">
            <Upload beforeUpload={() => false} maxCount={1}>
              <Button icon={<UploadOutlined />}>Upload Document</Button>
            </Upload>
            <Text type="secondary">Utility bill or bank statement within last 3 months</Text>
          </Form.Item>
        </Form>
      ),
    },
    {
      title: 'Selfie',
      icon: <UserOutlined />,
      content: (
        <div style={{ textAlign: 'center', padding: 40 }}>
          <Title level={4}>Take a Selfie</Title>
          <Text type="secondary">Hold your ID document next to your face</Text>
          <div style={{ margin: '20px 0' }}>
            <Upload beforeUpload={() => false} maxCount={1} accept="image/*">
              <Button icon={<UploadOutlined />} size="large">Upload Selfie</Button>
            </Upload>
          </div>
        </div>
      ),
    },
  ];

  const handleNext = () => {
    if (current < steps.length - 1) setCurrent(current + 1);
  };

  const handlePrev = () => {
    if (current > 0) setCurrent(current - 1);
  };

  const handleSubmit = () => {
    message.success('KYC submitted for review!');
    setCurrent(steps.length);
  };

  if (current === steps.length) {
    return (
      <Card>
        <Result status="success" title="KYC Submitted" subTitle="Your documents are under review. This usually takes 1-2 business days." />
      </Card>
    );
  }

  return (
    <Card>
      <Title level={4}>KYC Verification</Title>
      <Steps current={current} style={{ marginBottom: 24 }}>
        {steps.map((step, i) => (
          <Step key={i} title={step.title} icon={step.icon} />
        ))}
      </Steps>
      <div style={{ minHeight: 300 }}>{steps[current].content}</div>
      <div style={{ marginTop: 24, textAlign: 'right' }}>
        {current > 0 && <Button onClick={handlePrev} style={{ marginRight: 8 }}>Previous</Button>}
        {current < steps.length - 1 && <Button type="primary" onClick={handleNext}>Next</Button>}
        {current === steps.length - 1 && <Button type="primary" onClick={handleSubmit}>Submit</Button>}
      </div>
    </Card>
  );
}
