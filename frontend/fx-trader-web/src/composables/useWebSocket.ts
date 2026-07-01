import { useEffect, useRef, useCallback } from 'react';
import { io, Socket } from 'socket.io-client';
import { useUserStore } from '@/store/userStore';

interface Options { namespace?: string; onConnect?: () => void; onDisconnect?: () => void; }

export const useWebSocket = (options: Options = {}) => {
  const { namespace = '/', onConnect, onDisconnect } = options;
  const socketRef = useRef<Socket | null>(null);
  const token = useUserStore((s) => s.token);

  useEffect(() => {
    const wsUrl = import.meta.env.VITE_WS_URL || window.location.origin;
    const socket = io(`${wsUrl}${namespace}`, { auth: { token }, reconnection: true, reconnectionAttempts: 10, reconnectionDelay: 1000 });
    socketRef.current = socket;
    socket.on('connect', () => onConnect?.());
    socket.on('disconnect', () => onDisconnect?.());
    return () => { socket.disconnect(); socketRef.current = null; };
  }, [namespace, token]);

  const emit = useCallback((event: string, data?: any) => socketRef.current?.emit(event, data), []);
  const on = useCallback((event: string, cb: (...args: any[]) => void) => {
    socketRef.current?.on(event, cb);
    return () => { socketRef.current?.off(event, cb); };
  }, []);

  return { emit, on, socket: socketRef };
};
