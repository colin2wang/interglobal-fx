import axios from 'axios';
import { message } from 'antd';
import { getToken, removeToken } from '@/utils/storage';

const client = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL,
  timeout: 30000,
  headers: { 'Content-Type': 'application/json' },
});

client.interceptors.request.use(
  (config) => {
    const token = getToken();
    if (token) config.headers.Authorization = `Bearer ${token}`;
    return config;
  },
  (error) => Promise.reject(error),
);

client.interceptors.response.use(
  (response) => {
    const { data } = response;
    if (data.code !== 0 && data.code !== 200) {
      message.error(data.message || 'Request failed');
      if (data.code === 401) {
        removeToken();
        window.location.href = '/login';
      }
      return Promise.reject(new Error(data.message));
    }
    return data;
  },
  (error) => {
    if (error.response?.status === 401) {
      removeToken();
      window.location.href = '/login';
    }
    message.error(error.message || 'Network error');
    return Promise.reject(error);
  },
);

export default client;
