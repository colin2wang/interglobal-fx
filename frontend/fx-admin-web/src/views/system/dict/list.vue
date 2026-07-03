<template>
  <el-card>
    <template #header
      ><div style="display: flex; justify-content: space-between; align-items: center">
        <span>Dictionary</span><el-button type="primary">Add</el-button>
      </div></template
    >
    <el-table v-loading="loading" :data="dicts">
      <el-table-column prop="type" label="Type" />
      <el-table-column prop="name" label="Name" />
      <el-table-column prop="description" label="Description" />
    </el-table>
  </el-card>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import request from '@/api/axios';
const loading = ref(false);
const dicts = ref([]);
onMounted(async () => {
  loading.value = true;
  try {
    const res = await request.get('/system/dict/list');
    dicts.value = res.data;
  } finally {
    loading.value = false;
  }
});
</script>
