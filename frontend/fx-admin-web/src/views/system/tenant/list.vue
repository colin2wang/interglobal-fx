<template>
  <el-card>
    <template #header
      ><div style="display: flex; justify-content: space-between; align-items: center">
        <span>Tenant Management</span><el-button type="primary">Add Tenant</el-button>
      </div></template
    >
    <el-table v-loading="loading" :data="tenants">
      <el-table-column prop="id" label="ID" />
      <el-table-column prop="name" label="Name" />
      <el-table-column prop="code" label="Code" />
    </el-table>
  </el-card>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import request from '@/api/axios';
const loading = ref(false);
const tenants = ref([]);
onMounted(async () => {
  loading.value = true;
  try {
    const res = await request.get('/system/tenant/list');
    tenants.value = res.data;
  } finally {
    loading.value = false;
  }
});
</script>
