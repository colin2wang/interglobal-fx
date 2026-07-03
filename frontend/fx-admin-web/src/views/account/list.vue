<template>
  <el-card>
    <template #header
      ><div style="display: flex; justify-content: space-between; align-items: center">
        <span>Account List</span>
        <div>
          <el-button type="primary" @click="$router.push('/account/deposit')">Deposit</el-button
          ><el-button @click="$router.push('/account/withdraw')">Withdraw</el-button>
        </div>
      </div></template
    >
    <el-table v-loading="loading" :data="accounts">
      <el-table-column prop="id" label="ID" />
      <el-table-column prop="accountNo" label="Account No" />
      <el-table-column prop="balance" label="Balance"
        ><template #default="{ row }">${{ row.balance?.toFixed(2) }}</template></el-table-column
      >
      <el-table-column prop="equity" label="Equity"
        ><template #default="{ row }">${{ row.equity?.toFixed(2) }}</template></el-table-column
      >
      <el-table-column prop="leverage" label="Leverage" />
      <el-table-column prop="status" label="Status"
        ><template #default="{ row }"><StatusTag :status="row.status" /></template
      ></el-table-column>
    </el-table>
  </el-card>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import StatusTag from '@/components/common/StatusTag.vue';
import request from '@/api/axios';
const loading = ref(false);
const accounts = ref([]);
onMounted(async () => {
  loading.value = true;
  try {
    const res = await request.get('/admin/accounts');
    accounts.value = res.data;
  } finally {
    loading.value = false;
  }
});
</script>
