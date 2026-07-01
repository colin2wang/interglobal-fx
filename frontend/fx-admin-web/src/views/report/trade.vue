<template>
  <el-card title="Trade Report">
    <el-row :gutter="16" style="margin-bottom:16px">
      <el-col :span="6"><el-statistic title="Total Trades" :value="1284" /></el-col>
      <el-col :span="6"><el-statistic title="Total Volume" :value="5234.5" /></el-col>
      <el-col :span="6"><el-statistic title="Total Commission" :value="12340" prefix="$" /></el-col>
      <el-col :span="6"><el-statistic title="Net P/L" :value="8920" prefix="$" /></el-col>
    </el-row>
    <div ref="chartRef" style="height:400px"></div>
  </el-card>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import * as echarts from 'echarts';
const chartRef = ref();
onMounted(() => {
  if (chartRef.value) {
    const chart = echarts.init(chartRef.value);
    chart.setOption({ tooltip: { trigger: 'axis' }, xAxis: { type: 'category', data: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun'] }, yAxis: { type: 'value' }, series: [{ name: 'Volume', type: 'bar', data: [120, 200, 150, 80, 70, 110] }, { name: 'P/L', type: 'line', data: [40, 80, 60, 30, 25, 45] }] });
  }
});
</script>
