import { useEffect, useRef } from 'react';
import * as echarts from 'echarts';
import type { KlineData } from '@/api/quote';

interface Props {
  data: KlineData[];
  height?: number;
}

export const CandlestickChart = ({ data, height = 500 }: Props) => {
  const chartRef = useRef<HTMLDivElement>(null);
  const chartInstance = useRef<echarts.ECharts | null>(null);

  useEffect(() => {
    if (chartRef.current) chartInstance.current = echarts.init(chartRef.current);
    return () => {
      chartInstance.current?.dispose();
    };
  }, []);

  useEffect(() => {
    if (!chartInstance.current || !data.length) return;
    const dates = data.map((d) => new Date(d.time).toLocaleDateString());
    const ohlc = data.map((d) => [d.open, d.close, d.low, d.high]);
    const volumes = data.map((d) => d.volume);

    chartInstance.current.setOption({
      animation: false,
      tooltip: { trigger: 'axis', axisPointer: { type: 'cross' } },
      grid: [
        { left: '10%', right: '8%', height: '60%' },
        { left: '10%', right: '8%', top: '75%', height: '16%' },
      ],
      xAxis: [
        { type: 'category', data: dates, gridIndex: 0, axisLine: { lineStyle: { color: '#777' } } },
        { type: 'category', data: dates, gridIndex: 1, axisLine: { lineStyle: { color: '#777' } } },
      ],
      yAxis: [
        { type: 'value', gridIndex: 0, splitLine: { lineStyle: { color: '#eee' } } },
        { type: 'value', gridIndex: 1, splitLine: { show: false } },
      ],
      dataZoom: [
        { type: 'inside', xAxisIndex: [0, 1], start: 50, end: 100 },
        { type: 'slider', xAxisIndex: [0, 1], start: 50, end: 100, bottom: 5 },
      ],
      series: [
        {
          name: 'K-Line',
          type: 'candlestick',
          xAxisIndex: 0,
          yAxisIndex: 0,
          data: ohlc,
          itemStyle: {
            color: '#ef5350',
            color0: '#26a69a',
            borderColor: '#ef5350',
            borderColor0: '#26a69a',
          },
        },
        {
          name: 'Volume',
          type: 'bar',
          xAxisIndex: 1,
          yAxisIndex: 1,
          data: volumes,
          itemStyle: {
            color: (params: any) => {
              const d = data[params.dataIndex];
              return d.close >= d.open ? '#ef5350' : '#26a69a';
            },
          },
        },
      ],
    });
  }, [data]);

  return <div ref={chartRef} style={{ width: '100%', height }} />;
};
