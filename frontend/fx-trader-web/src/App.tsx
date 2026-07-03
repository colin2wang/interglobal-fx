import { Suspense } from 'react';
import { useRoutes } from 'react-router-dom';
import { Spin } from 'antd';
import { ErrorBoundary } from './components/common/ErrorBoundary';
import { routes } from './router/routes';

function App() {
  const element = useRoutes(routes);
  return (
    <ErrorBoundary>
      <Suspense fallback={<Spin size="large" className="page-loading" />}>{element}</Suspense>
    </ErrorBoundary>
  );
}

export default App;
