<template>
  <el-card>
    <template #header><div style="display:flex;justify-content:space-between;align-items:center"><span>Blacklist</span><el-button type="primary">Add</el-button></div></template>
    <el-table :data="blacklist" v-loading="loading">
      <el-table-column prop="id" label="ID" />
      <el-table-column prop="identifier" label="Identifier" />
      <el-table-column prop="type" label="Type" />
      <el-table-column prop="reason" label="Reason" />
      <el-table-column label="Actions"><template #default="{row}"><el-button type="danger" link @click="handleRemove(row.id)">Remove</el-button></template></el-table-column>
    </el-table>
  </el-card>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { ElMessage } from 'element-plus';
import { riskApi } from '@/api';
const loading = ref(false);
const blacklist = ref([]);
const fetchData = async () => { loading.value = true; try { const res = await riskApi.getBlacklist({ page: 1, pageSize: 20 }); blacklist.value = res.data.list; } finally { loading.value = false; } };
const handleRemove = async (id: number) => { await riskApi.removeFromBlacklist(id); ElMessage.success('Removed'); fetchData(); };
onMounted(fetchData);
</script>
