<template>
  <el-card>
    <template #header
      ><div style="display: flex; justify-content: space-between; align-items: center">
        <span>User Management</span
        ><el-button type="primary" @click="$router.push('/system/user/form')">Add User</el-button>
      </div></template
    >
    <el-table v-loading="loading" :data="users">
      <el-table-column prop="id" label="ID" />
      <el-table-column prop="username" label="Username" />
      <el-table-column prop="nickname" label="Nickname" />
      <el-table-column prop="email" label="Email" />
      <el-table-column prop="status" label="Status"
        ><template #default="{ row }"><StatusTag :status="row.status" /></template
      ></el-table-column>
      <el-table-column label="Actions" width="200"
        ><template #default="{ row }"
          ><el-button type="primary" link @click="$router.push(`/system/user/form/${row.id}`)"
            >Edit</el-button
          ><el-button type="danger" link @click="handleDelete(row.id)">Delete</el-button></template
        ></el-table-column
      >
    </el-table>
  </el-card>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { ElMessage, ElMessageBox } from 'element-plus';
import StatusTag from '@/components/common/StatusTag.vue';
import { userApi } from '@/api';
const loading = ref(false);
const users = ref([]);
const fetchData = async () => {
  loading.value = true;
  try {
    const res = await userApi.getList({ page: 1, pageSize: 20 });
    users.value = res.data.list;
  } finally {
    loading.value = false;
  }
};
const handleDelete = async (id: number) => {
  await ElMessageBox.confirm('Delete?');
  await userApi.delete(id);
  ElMessage.success('Deleted');
  fetchData();
};
onMounted(fetchData);
</script>
