import { create } from 'zustand';
import type { Order } from '@/api/order';

interface OrderState {
  pendingOrders: Order[];
  orderHistory: Order[];
  setPendingOrders: (orders: Order[]) => void;
  setOrderHistory: (orders: Order[]) => void;
  addPendingOrder: (order: Order) => void;
  removePendingOrder: (orderId: string) => void;
}

export const useOrderStore = create<OrderState>((set) => ({
  pendingOrders: [],
  orderHistory: [],
  setPendingOrders: (orders) => set({ pendingOrders: orders }),
  setOrderHistory: (orders) => set({ orderHistory: orders }),
  addPendingOrder: (order) => set((state) => ({ pendingOrders: [...state.pendingOrders, order] })),
  removePendingOrder: (orderId) => set((state) => ({ pendingOrders: state.pendingOrders.filter((o) => o.id !== orderId) })),
}));
