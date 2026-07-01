import { useCallback } from 'react';
import { useNavigate } from 'react-router-dom';
import { message } from 'antd';
import { useUserStore } from '@/store/userStore';
import client from '@/api/client';

export const useAuth = () => {
  const { setToken, setUserInfo, logout, isLoggedIn } = useUserStore();
  const navigate = useNavigate();

  const login = useCallback(async (username: string, password: string) => {
    try {
      const res = await client.post('/auth/login', { username, password });
      setToken(res.data.token);
      setUserInfo(res.data.user);
      message.success('Login successful');
      navigate('/trading');
    } catch { message.error('Login failed'); throw new Error('Login failed'); }
  }, [setToken, setUserInfo, navigate]);

  const logoutUser = useCallback(() => { logout(); navigate('/login'); }, [logout, navigate]);

  return { login, logout: logoutUser, isLoggedIn };
};
