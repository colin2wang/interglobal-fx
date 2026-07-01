import axios from 'axios';
import { ElMessage } from 'element-plus';
import { getToken, removeToken } from '@/utils/auth';
import router from '@/router';

const service = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL,
  timeout: 30000,
});

service.interceptors.request.use(
  (config) => {
    const token = getToken();
    if (token) config.headers.Authorization = `Bearer ${token}`;
    return config;
  },
  (error) => Promise.reject(error),
);

service.interceptors.response.use(
  (response) => {
    const res = response.data;
    if (res.code !== 0 && res.code !== 200) {
      ElMessage.error(res.message || 'Error');
      if (res.code === 401) { removeToken(); router.push('/login'); }
      return Promise.reject(new Error(res.message));
    }
    return res;
  },
  (error) => {
    ElMessage.error(error.message || 'Network Error');
    return Promise.reject(error);
  },
);

export default service;
