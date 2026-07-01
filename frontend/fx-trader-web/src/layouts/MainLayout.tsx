import { Layout } from 'antd';
import { Outlet } from 'react-router-dom';
import { Header } from './components/Header';
import { Sidebar } from './components/Sidebar';

const { Content } = Layout;

export const MainLayout = () => (
  <Layout style={{ minHeight: '100vh' }}>
    <Sidebar />
    <Layout>
      <Header />
      <Content style={{ margin: 16, padding: 16, background: '#fff', borderRadius: 8 }}>
        <Outlet />
      </Content>
    </Layout>
  </Layout>
);
