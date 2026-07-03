<template>
  <div>
    <el-row :gutter="16">
      <el-col :span="6"
        ><el-card shadow="hover"><el-statistic title="Total Customers" :value="1284" /></el-card
      ></el-col>
      <el-col :span="6"
        ><el-card shadow="hover"><el-statistic title="Total Accounts" :value="2560" /></el-card
      ></el-col>
      <el-col :span="6"
        ><el-card shadow="hover"><el-statistic title="Active Orders" :value="156" /></el-card
      ></el-col>
      <el-col :span="6"
        ><el-card shadow="hover"
          ><el-statistic title="Today's Volume" :value="1289342" prefix="$" /></el-card
      ></el-col>
    </el-row>
    <el-row :gutter="16" style="margin-top: 16px">
      <el-col :span="16"
        ><el-card title="Trading Volume"><div ref="chartRef" style="height: 400px"></div></el-card
      ></el-col>
      <el-col :span="8">
        <el-card title="Recent Orders">
          <el-table :data="recentOrders" size="small">
            <el-table-column prop="symbol" label="Symbol" />
            <el-table-column prop="side" label="Side"
              ><template #default="{ row }"
                ><el-tag :type="row.side === 'buy' ? 'success' : 'danger'" size="small">{{
                  row.side.toUpperCase()
                }}</el-tag></template
              ></el-table-column
            >
            <el-table-column prop="volume" label="Volume" />
          </el-table>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import * as echarts from 'echarts';
const chartRef = ref();
const recentOrders = ref([
  { symbol: 'EUR/USD', side: 'buy', volume: 0.1 },
  { symbol: 'GBP/USD', side: 'sell', volume: 0.5 },
]);

onMounted(() => {
  if (chartRef.value) {
    const chart = echarts.init(chartRef.value);
    chart.setOption({
      tooltip: { trigger: 'axis' },
      xAxis: { type: 'category', data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri'] },
      yAxis: { type: 'value' },
      series: [{ type: 'bar', data: [120, 200, 150, 80, 70] }],
    });
  }
});
</script>
