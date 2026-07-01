<template>
  <el-card>
    <template #header><span>Customer List</span></template>
    <el-table :data="customers" v-loading="loading">
      <el-table-column prop="id" label="ID" />
      <el-table-column prop="username" label="Username" />
      <el-table-column prop="email" label="Email" />
      <el-table-column prop="kycStatus" label="KYC"><template #default="{row}"><StatusTag :status="row.kycStatus" /></template></el-table-column>
      <el-table-column label="Actions"><template #default="{row}"><el-button type="primary" link @click="$router.push(`/customer/${row.id}`)">View</el-button></template></el-table-column>
    </el-table>
  </el-card>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import StatusTag from '@/components/common/StatusTag.vue';
import { customerApi } from '@/api';
const loading = ref(false);
const customers = ref([]);
onMounted(async () => { loading.value = true; try { const res = await customerApi.getList({ page: 1, pageSize: 20 }); customers.value = res.data.list; } finally { loading.value = false; } });
</script>
