<template>
  <el-card>
    <template #header>
      <div style="display: flex; justify-content: space-between; align-items: center">
        <span>Customer Report</span>
        <el-button type="primary" @click="handleExport">Export Excel</el-button>
      </div>
    </template>
    <el-form :inline="true" :model="query" class="search-form">
      <el-form-item label="Date Range">
        <el-date-picker v-model="query.dateRange" type="daterange" range-separator="-" />
      </el-form-item>
      <el-form-item label="Customer Type">
        <el-select v-model="query.customerType" clearable placeholder="All">
          <el-option label="Retail" value="retail" />
          <el-option label="Professional" value="professional" />
          <el-option label="Institutional" value="institutional" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary">Search</el-button>
      </el-form-item>
    </el-form>
    <el-table v-loading="loading" :data="reportData">
      <el-table-column prop="customerName" label="Customer" />
      <el-table-column prop="customerType" label="Type" />
      <el-table-column prop="totalTrades" label="Total Trades" />
      <el-table-column prop="totalVolume" label="Total Volume" />
      <el-table-column prop="totalDeposit" label="Total Deposit" />
      <el-table-column prop="totalWithdrawal" label="Total Withdrawal" />
      <el-table-column prop="netPnl" label="Net P&L">
        <template #default="{ row }">
          <span :style="{ color: row.netPnl >= 0 ? '#67c23a' : '#f56c6c' }">
            {{ row.netPnl >= 0 ? '+' : '' }}{{ row.netPnl }}
          </span>
        </template>
      </el-table-column>
    </el-table>
  </el-card>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { ElMessage } from 'element-plus';

const loading = ref(false);
const query = ref({ dateRange: [], customerType: '' });
const reportData = ref([
  { customerName: 'John Smith', customerType: 'Retail', totalTrades: 156, totalVolume: '$2.5M', totalDeposit: '$50,000', totalWithdrawal: '$12,000', netPnl: 3200 },
  { customerName: 'Jane Doe', customerType: 'Professional', totalTrades: 423, totalVolume: '$15.8M', totalDeposit: '$200,000', totalWithdrawal: '$45,000', netPnl: 12500 },
  { customerName: 'Corp Trading Ltd', customerType: 'Institutional', totalTrades: 89, totalVolume: '$50.2M', totalDeposit: '$1,000,000', totalWithdrawal: '$0', netPnl: 45000 },
]);

const handleExport = () => { ElMessage.info('Export feature coming soon'); };
onMounted(() => { loading.value = true; setTimeout(() => { loading.value = false; }, 500); });
</script>
