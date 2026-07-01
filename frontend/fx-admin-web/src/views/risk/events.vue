<template>
  <el-card title="Risk Events">
    <el-table :data="events" v-loading="loading">
      <el-table-column prop="id" label="ID" />
      <el-table-column prop="type" label="Type" />
      <el-table-column prop="level" label="Level"><template #default="{row}"><el-tag :type="row.level==='high'?'danger':row.level==='medium'?'warning':'info'">{{row.level}}</el-tag></template></el-table-column>
      <el-table-column prop="accountNo" label="Account" />
      <el-table-column prop="description" label="Description" />
      <el-table-column prop="createdAt" label="Time" />
    </el-table>
  </el-card>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { riskApi } from '@/api';
const loading = ref(false);
const events = ref([]);
onMounted(async () => { loading.value = true; try { const res = await riskApi.getEvents({ page: 1, pageSize: 20 }); events.value = res.data.list; } finally { loading.value = false; } });
</script>
