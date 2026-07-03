import { Spin } from 'antd';

export const PageLoading = ({ tip = 'Loading...' }: { tip?: string }) => (
  <div className="page-loading">
    <Spin size="large" tip={tip} />
  </div>
);
