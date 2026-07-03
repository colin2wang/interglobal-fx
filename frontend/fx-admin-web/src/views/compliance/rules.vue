<template>
  <el-card>
    <template #header>
      <div style="display: flex; justify-content: space-between; align-items: center">
        <span>Compliance Rules</span>
        <el-button type="primary">Add Rule</el-button>
      </div>
    </template>
    <el-table v-loading="loading" :data="rules">
      <el-table-column prop="id" label="ID" />
      <el-table-column prop="name" label="Rule Name" />
      <el-table-column prop="type" label="Type" />
      <el-table-column prop="region" label="Region" />
      <el-table-column prop="description" label="Description" show-overflow-tooltip />
      <el-table-column prop="status" label="Status">
        <template #default="{ row }">
          <el-tag :type="row.status === 1 ? 'success' : 'danger'">
            {{ row.status === 1 ? 'Active' : 'Inactive' }}
          </el-tag>
        </template>
      </el-table-column>
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
const rules = ref([
  { id: 1, name: 'ESMA Leverage Limit', type: 'Leverage', region: 'EU', description: 'Max leverage 1:30 for retail clients', status: 1 },
  { id: 2, name: 'FCA Negative Balance Protection', type: 'Balance', region: 'UK', description: 'Clients cannot lose more than deposited', status: 1 },
  { id: 3, name: 'ASIC Spread Control', type: 'Spread', region: 'AU', description: 'Maximum spread limits per instrument', status: 1 },
]);

const handleDelete = async (id: number) => {
  await ElMessageBox.confirm('Delete this compliance rule?');
  rules.value = rules.value.filter(r => r.id !== id);
  ElMessage.success('Deleted');
};

onMounted(() => { loading.value = true; setTimeout(() => { loading.value = false; }, 500); });
</script>
