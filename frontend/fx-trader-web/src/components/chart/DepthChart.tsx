import { useEffect, useRef } from 'react';
import * as echarts from 'echarts';

interface DepthLevel {
  price: number;
  amount: number;
}
interface Props {
  bids: DepthLevel[];
  asks: DepthLevel[];
  height?: number;
}

export const DepthChart = ({ bids, asks, height = 300 }: Props) => {
  const chartRef = useRef<HTMLDivElement>(null);
  const chartInstance = useRef<echarts.ECharts | null>(null);

  useEffect(() => {
    if (chartRef.current) chartInstance.current = echarts.init(chartRef.current);
    return () => {
      chartInstance.current?.dispose();
    };
  }, []);

  useEffect(() => {
    if (!chartInstance.current) return;
    const bidPrices = bids.map((b) => b.price);
    const bidVolumes = bids.map((b) => b.amount);
    const askPrices = asks.map((a) => a.price);
    const askVolumes = asks.map((a) => a.amount);

    chartInstance.current.setOption({
      tooltip: { trigger: 'axis' },
      legend: { data: ['Bids', 'Asks'] },
      xAxis: {
        type: 'category',
        data: [...bidPrices.reverse(), ...askPrices],
        axisLabel: { formatter: (v: any) => Number(v).toFixed(5) },
      },
      yAxis: { type: 'value' },
      series: [
        {
          name: 'Bids',
          type: 'line',
          data: [...bidVolumes.reverse()],
          areaStyle: { color: 'rgba(38,166,154,0.3)' },
          lineStyle: { color: '#26a69a' },
          itemStyle: { color: '#26a69a' },
        },
        {
          name: 'Asks',
          type: 'line',
          data: [...askVolumes],
          areaStyle: { color: 'rgba(239,83,80,0.3)' },
          lineStyle: { color: '#ef5350' },
          itemStyle: { color: '#ef5350' },
        },
      ],
    });
  }, [bids, asks]);

  return <div ref={chartRef} style={{ width: '100%', height }} />;
};
