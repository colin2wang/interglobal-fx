<template>
  <el-card>
    <template #header>
      <div style="display: flex; justify-content: space-between; align-items: center">
        <span>Risk Report</span>
        <el-button type="primary" @click="handleExport">Export Excel</el-button>
      </div>
    </template>
    <el-form :inline="true" :model="query" class="search-form">
      <el-form-item label="Date Range">
        <el-date-picker v-model="query.dateRange" type="daterange" range-separator="-" />
      </el-form-item>
      <el-form-item label="Event Type">
        <el-select v-model="query.eventType" clearable placeholder="All">
          <el-option label="Order Rejected" value="order_rejected" />
          <el-option label="Forced Close" value="forced_close" />
          <el-option label="Account Frozen" value="account_frozen" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary">Search</el-button>
      </el-form-item>
    </el-form>
    <el-table v-loading="loading" :data="reportData">
      <el-table-column prop="date" label="Date" />
      <el-table-column prop="totalEvents" label="Total Events" />
      <el-table-column prop="orderRejected" label="Order Rejected" />
      <el-table-column prop="forcedClose" label="Forced Close" />
      <el-table-column prop="accountFrozen" label="Account Frozen" />
      <el-table-column prop="totalExposure" label="Total Exposure" />
      <el-table-column prop="riskScore" label="Risk Score">
        <template #default="{ row }">
          <el-tag :type="row.riskScore > 80 ? 'danger' : row.riskScore > 50 ? 'warning' : 'success'">
            {{ row.riskScore }}
          </el-tag>
        </template>
      </el-table-column>
    </el-table>
  </el-card>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { ElMessage } from 'element-plus';

const loading = ref(false);
const query = ref({ dateRange: [], eventType: '' });
const reportData = ref([
  { date: '2024-01-15', totalEvents: 23, orderRejected: 8, forcedClose: 3, accountFrozen: 2, totalExposure: '$1.2M', riskScore: 65 },
  { date: '2024-01-16', totalEvents: 31, orderRejected: 12, forcedClose: 5, accountFrozen: 1, totalExposure: '$2.5M', riskScore: 78 },
  { date: '2024-01-17', totalEvents: 15, orderRejected: 5, forcedClose: 2, accountFrozen: 0, totalExposure: '$800K', riskScore: 42 },
]);

const handleExport = () => { ElMessage.info('Export feature coming soon'); };
onMounted(() => { loading.value = true; setTimeout(() => { loading.value = false; }, 500); });
</script>
