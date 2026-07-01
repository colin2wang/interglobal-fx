import client from '@/api/client';

interface RequestOptions {
  url: string;
  method?: 'get' | 'post' | 'put' | 'delete';
  data?: any;
  params?: any;
}

export const request = async <T = any>(options: RequestOptions): Promise<T> => {
  const { url, method = 'get', data, params } = options;
  const response = await client.request<T>({ url, method, data, params });
  return response.data;
};
