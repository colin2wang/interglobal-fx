<template>
  <el-card title="Audit Log">
    <el-table :data="audits" v-loading="loading">
      <el-table-column prop="id" label="ID" />
      <el-table-column prop="operator" label="Operator" />
      <el-table-column prop="action" label="Action" />
      <el-table-column prop="module" label="Module" />
      <el-table-column prop="ip" label="IP" />
      <el-table-column prop="createdAt" label="Time" />
    </el-table>
  </el-card>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import request from '@/api/axios';
const loading = ref(false);
const audits = ref([]);
onMounted(async () => { loading.value = true; try { const res = await request.get('/system/audit/list'); audits.value = res.data; } finally { loading.value = false; } });
</script>
