<template>
  <el-card>
    <template #header>
      <div style="display: flex; justify-content: space-between; align-items: center">
        <span>Spread Configuration</span>
        <el-button type="primary">Add Symbol</el-button>
      </div>
    </template>
    <el-table v-loading="loading" :data="spreads">
      <el-table-column prop="symbol" label="Symbol" />
      <el-table-column prop="spreadType" label="Spread Type">
        <template #default="{ row }">
          <el-tag :type="row.spreadType === 'fixed' ? 'info' : 'success'">{{ row.spreadType }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="baseSpread" label="Base Spread (pts)" />
      <el-table-column prop="markup" label="Markup (pts)" />
      <el-table-column prop="minSpread" label="Min Spread (pts)" />
      <el-table-column prop="maxSpread" label="Max Spread (pts)" />
      <el-table-column prop="status" label="Status">
        <template #default="{ row }">
          <el-switch v-model="row.status" :active-value="1" :inactive-value="0" />
        </template>
      </el-table-column>
      <el-table-column label="Actions">
        <template #default>
          <el-button type="primary" link>Edit</el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-card>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';

const loading = ref(false);
const spreads = ref([
  { symbol: 'EURUSD', spreadType: 'variable', baseSpread: 8, markup: 2, minSpread: 5, maxSpread: 20, status: 1 },
  { symbol: 'GBPUSD', spreadType: 'variable', baseSpread: 10, markup: 3, minSpread: 7, maxSpread: 25, status: 1 },
  { symbol: 'USDJPY', spreadType: 'variable', baseSpread: 9, markup: 2, minSpread: 6, maxSpread: 22, status: 1 },
  { symbol: 'XAUUSD', spreadType: 'fixed', baseSpread: 25, markup: 5, minSpread: 25, maxSpread: 25, status: 1 },
]);

onMounted(() => { loading.value = true; setTimeout(() => { loading.value = false; }, 500); });
</script>
