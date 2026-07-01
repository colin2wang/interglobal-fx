<template>
  <el-card title="Support Tickets">
    <el-table :data="tickets" v-loading="loading">
      <el-table-column prop="id" label="ID" />
      <el-table-column prop="subject" label="Subject" />
      <el-table-column prop="customer" label="Customer" />
      <el-table-column prop="status" label="Status"><template #default="{row}"><StatusTag :status="row.status" /></template></el-table-column>
      <el-table-column prop="createdAt" label="Created" />
    </el-table>
  </el-card>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import StatusTag from '@/components/common/StatusTag.vue';
import request from '@/api/axios';
const loading = ref(false);
const tickets = ref([]);
onMounted(async () => { loading.value = true; try { const res = await request.get('/admin/tickets'); tickets.value = res.data; } finally { loading.value = false; } });
</script>
