import dayjs from 'dayjs';

export const formatMoney = (v: number, d = 2) =>
  new Intl.NumberFormat('en-US', { minimumFractionDigits: d, maximumFractionDigits: d }).format(v);
export const formatDate = (d: string | number | Date, fmt = 'YYYY-MM-DD HH:mm:ss') =>
  dayjs(d).format(fmt);
