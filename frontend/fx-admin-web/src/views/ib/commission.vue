<template>
  <el-card title="IB Commission">
    <el-table v-loading="loading" :data="commissions">
      <el-table-column prop="id" label="ID" />
      <el-table-column prop="ibName" label="IB" />
      <el-table-column prop="client" label="Client" />
      <el-table-column prop="symbol" label="Symbol" />
      <el-table-column prop="volume" label="Volume" />
      <el-table-column prop="commission" label="Commission"
        ><template #default="{ row }">${{ row.commission?.toFixed(2) }}</template></el-table-column
      >
      <el-table-column prop="createdAt" label="Date" />
    </el-table>
  </el-card>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import request from '@/api/axios';
const loading = ref(false);
const commissions = ref([]);
onMounted(async () => {
  loading.value = true;
  try {
    const res = await request.get('/admin/ib/commissions');
    commissions.value = res.data;
  } finally {
    loading.value = false;
  }
});
</script>
