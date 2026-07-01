<template>
  <el-card>
    <template #header><div style="display:flex;justify-content:space-between;align-items:center"><span>Menu Management</span><el-button type="primary">Add Menu</el-button></div></template>
    <el-table :data="menus" v-loading="loading" row-key="id" default-expand-all>
      <el-table-column prop="name" label="Name" />
      <el-table-column prop="path" label="Path" />
      <el-table-column prop="icon" label="Icon" />
      <el-table-column prop="sort" label="Sort" />
    </el-table>
  </el-card>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import request from '@/api/axios';
const loading = ref(false);
const menus = ref([]);
onMounted(async () => { loading.value = true; try { const res = await request.get('/system/menu/list'); menus.value = res.data; } finally { loading.value = false; } });
</script>
