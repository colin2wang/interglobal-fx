<template>
  <el-card title="Pending Orders">
    <el-table :data="orders" v-loading="loading">
      <el-table-column prop="id" label="ID" />
      <el-table-column prop="symbol" label="Symbol" />
      <el-table-column prop="side" label="Side"><template #default="{row}"><el-tag :type="row.side==='buy'?'success':'danger'">{{row.side.toUpperCase()}}</el-tag></template></el-table-column>
      <el-table-column prop="type" label="Type" />
      <el-table-column prop="volume" label="Volume" />
      <el-table-column prop="price" label="Price" />
      <el-table-column label="Actions"><template #default="{row}"><el-button type="danger" link @click="handleCancel(row.id)">Cancel</el-button></template></el-table-column>
    </el-table>
  </el-card>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { ElMessage } from 'element-plus';
import { orderApi } from '@/api';

const loading = ref(false);
const orders = ref([]);
const fetchData = async () => { loading.value = true; try { const res = await orderApi.getList({ status: 'pending' }); orders.value = res.data.list; } finally { loading.value = false; } };
const handleCancel = async (id: string) => { await orderApi.cancel(id); ElMessage.success('Cancelled'); fetchData(); };
onMounted(fetchData);
</script>
