import { Typography } from 'antd';
import { useEffect, useState } from 'react';

interface Props { price: number; prevPrice?: number; decimals?: number; size?: 'small' | 'middle' | 'large'; }

export const PriceDisplay = ({ price, prevPrice, decimals = 5, size = 'middle' }: Props) => {
  const [flash, setFlash] = useState<'up' | 'down' | null>(null);
  useEffect(() => {
    if (prevPrice !== undefined && prevPrice !== price) {
      setFlash(price > prevPrice ? 'up' : 'down');
      const t = setTimeout(() => setFlash(null), 500);
      return () => clearTimeout(t);
    }
  }, [price, prevPrice]);
  const cls = flash === 'up' ? 'price-up' : flash === 'down' ? 'price-down' : '';
  const fontSize = size === 'large' ? 24 : size === 'middle' ? 16 : 12;
  return <Typography.Text className={cls} style={{ fontSize }}>{price.toFixed(decimals)}</Typography.Text>;
};
