<template>
  <el-card title="Introducing Brokers">
    <el-table v-loading="loading" :data="ibList">
      <el-table-column prop="id" label="ID" />
      <el-table-column prop="name" label="Name" />
      <el-table-column prop="email" label="Email" />
      <el-table-column prop="clientCount" label="Clients" />
      <el-table-column prop="commission" label="Commission"
        ><template #default="{ row }">${{ row.commission?.toFixed(2) }}</template></el-table-column
      >
      <el-table-column prop="status" label="Status"
        ><template #default="{ row }"><StatusTag :status="row.status" /></template
      ></el-table-column>
    </el-table>
  </el-card>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import StatusTag from '@/components/common/StatusTag.vue';
import request from '@/api/axios';
const loading = ref(false);
const ibList = ref([]);
onMounted(async () => {
  loading.value = true;
  try {
    const res = await request.get('/admin/ib/list');
    ibList.value = res.data;
  } finally {
    loading.value = false;
  }
});
</script>
