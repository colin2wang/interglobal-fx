import { useCallback } from 'react';
import { message } from 'antd';
import { orderApi } from '@/api';
import type { CreateOrderParams } from '@/api/order';
import { useOrderStore } from '@/store/orderStore';

export const useOrder = () => {
  const { addPendingOrder, removePendingOrder } = useOrderStore();

  const createOrder = useCallback(
    async (params: CreateOrderParams) => {
      try {
        const order = await orderApi.createOrder(params);
        addPendingOrder(order);
        message.success('Order placed');
        return order;
      } catch (e) {
        message.error('Failed');
        throw e;
      }
    },
    [addPendingOrder],
  );

  const cancelOrder = useCallback(
    async (orderId: string) => {
      try {
        await orderApi.cancelOrder(orderId);
        removePendingOrder(orderId);
        message.success('Cancelled');
      } catch (e) {
        message.error('Failed');
        throw e;
      }
    },
    [removePendingOrder],
  );

  return { createOrder, cancelOrder };
};
