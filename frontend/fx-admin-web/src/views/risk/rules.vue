<template>
  <el-card>
    <template #header
      ><div style="display: flex; justify-content: space-between; align-items: center">
        <span>Risk Rules</span><el-button type="primary">Add Rule</el-button>
      </div></template
    >
    <el-table v-loading="loading" :data="rules">
      <el-table-column prop="id" label="ID" />
      <el-table-column prop="name" label="Name" />
      <el-table-column prop="type" label="Type" />
      <el-table-column prop="condition" label="Condition" />
      <el-table-column prop="action" label="Action" />
      <el-table-column prop="status" label="Status"
        ><template #default="{ row }"
          ><el-switch v-model="row.status" :active-value="1" :inactive-value="0" /></template
      ></el-table-column>
      <el-table-column label="Actions"
        ><template #default="{ row }"
          ><el-button type="primary" link>Edit</el-button
          ><el-button type="danger" link @click="handleDelete(row.id)">Delete</el-button></template
        ></el-table-column
      >
    </el-table>
  </el-card>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { ElMessage, ElMessageBox } from 'element-plus';
import { riskApi } from '@/api';
const loading = ref(false);
const rules = ref([]);
const fetchData = async () => {
  loading.value = true;
  try {
    const res = await riskApi.getRules({ page: 1, pageSize: 20 });
    rules.value = res.data.list;
  } finally {
    loading.value = false;
  }
};
const handleDelete = async (id: number) => {
  await ElMessageBox.confirm('Delete?');
  await riskApi.deleteRule(id);
  ElMessage.success('Deleted');
  fetchData();
};
onMounted(fetchData);
</script>
