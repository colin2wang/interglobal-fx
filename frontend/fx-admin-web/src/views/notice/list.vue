<template>
  <el-card>
    <template #header>
      <div style="display: flex; justify-content: space-between; align-items: center">
        <span>Announcements</span>
        <el-button type="primary">Create Announcement</el-button>
      </div>
    </template>
    <el-table v-loading="loading" :data="notices">
      <el-table-column prop="id" label="ID" />
      <el-table-column prop="title" label="Title" />
      <el-table-column prop="type" label="Type">
        <template #default="{ row }">
          <el-tag :type="row.type === 'urgent' ? 'danger' : row.type === 'maintenance' ? 'warning' : 'info'">
            {{ row.type }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="status" label="Status">
        <template #default="{ row }">
          <el-tag :type="row.status === 'published' ? 'success' : 'info'">
            {{ row.status }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="publishTime" label="Publish Time" />
      <el-table-column label="Actions">
        <template #default="{ row }">
          <el-button type="primary" link>Edit</el-button>
          <el-button type="danger" link @click="handleDelete(row.id)">Delete</el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-card>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { ElMessage, ElMessageBox } from 'element-plus';

const loading = ref(false);
const notices = ref([
  { id: 1, title: 'System Maintenance Notice', type: 'maintenance', status: 'published', publishTime: '2024-01-15 10:00' },
  { id: 2, title: 'New Currency Pairs Available', type: 'info', status: 'published', publishTime: '2024-01-14 09:00' },
  { id: 3, title: 'Holiday Trading Hours', type: 'urgent', status: 'draft', publishTime: '-' },
]);

const handleDelete = async (id: number) => {
  await ElMessageBox.confirm('Delete this announcement?');
  notices.value = notices.value.filter(n => n.id !== id);
  ElMessage.success('Deleted');
};

onMounted(() => { loading.value = true; setTimeout(() => { loading.value = false; }, 500); });
</script>
