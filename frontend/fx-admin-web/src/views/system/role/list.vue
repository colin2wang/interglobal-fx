<template>
  <el-card>
    <template #header
      ><div style="display: flex; justify-content: space-between; align-items: center">
        <span>Role Management</span><el-button type="primary">Add Role</el-button>
      </div></template
    >
    <el-table v-loading="loading" :data="roles">
      <el-table-column prop="id" label="ID" />
      <el-table-column prop="name" label="Name" />
      <el-table-column prop="code" label="Code" />
      <el-table-column prop="description" label="Description" />
      <el-table-column prop="status" label="Status"
        ><template #default="{ row }"><StatusTag :status="row.status" /></template
      ></el-table-column>
    </el-table>
  </el-card>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import StatusTag from '@/components/common/StatusTag.vue';
import { roleApi } from '@/api';
const loading = ref(false);
const roles = ref([]);
onMounted(async () => {
  loading.value = true;
  try {
    const res = await roleApi.getList({ page: 1, pageSize: 20 });
    roles.value = res.data.list;
  } finally {
    loading.value = false;
  }
});
</script>
