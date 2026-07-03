<template>
  <el-card title="Positions">
    <el-table v-loading="loading" :data="positions">
      <el-table-column prop="id" label="ID" />
      <el-table-column prop="accountNo" label="Account" />
      <el-table-column prop="symbol" label="Symbol" />
      <el-table-column prop="side" label="Side"
        ><template #default="{ row }"
          ><el-tag :type="row.side === 'buy' ? 'success' : 'danger'">{{
            row.side.toUpperCase()
          }}</el-tag></template
        ></el-table-column
      >
      <el-table-column prop="volume" label="Volume" />
      <el-table-column prop="openPrice" label="Open Price" />
      <el-table-column prop="currentPrice" label="Current" />
      <el-table-column prop="profit" label="P/L"
        ><template #default="{ row }"
          ><span :style="{ color: row.profit >= 0 ? '#67c23a' : '#f56c6c' }">{{
            row.profit?.toFixed(2)
          }}</span></template
        ></el-table-column
      >
    </el-table>
  </el-card>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import request from '@/api/axios';
const loading = ref(false);
const positions = ref([]);
onMounted(async () => {
  loading.value = true;
  try {
    const res = await request.get('/admin/positions');
    positions.value = res.data;
  } finally {
    loading.value = false;
  }
});
</script>
