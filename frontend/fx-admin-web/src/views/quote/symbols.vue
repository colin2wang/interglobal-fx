<template>
  <el-card title="Quote Symbols">
    <el-table v-loading="loading" :data="symbols">
      <el-table-column prop="symbol" label="Symbol" />
      <el-table-column prop="name" label="Name" />
      <el-table-column prop="category" label="Category" />
      <el-table-column prop="digits" label="Digits" />
      <el-table-column prop="spread" label="Spread" />
      <el-table-column prop="status" label="Status"
        ><template #default="{ row }"
          ><el-switch v-model="row.status" :active-value="1" :inactive-value="0" /></template
      ></el-table-column>
    </el-table>
  </el-card>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import request from '@/api/axios';
const loading = ref(false);
const symbols = ref([]);
onMounted(async () => {
  loading.value = true;
  try {
    const res = await request.get('/admin/quote/symbols');
    symbols.value = res.data;
  } finally {
    loading.value = false;
  }
});
</script>
