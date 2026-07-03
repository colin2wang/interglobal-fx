<template>
  <el-card>
    <template #header>
      <div style="display: flex; justify-content: space-between; align-items: center">
        <span>AML Monitoring</span>
        <el-button type="primary" @click="fetchData">Refresh</el-button>
      </div>
    </template>
    <el-form :inline="true" :model="query" class="search-form">
      <el-form-item label="Status">
        <el-select v-model="query.status" clearable placeholder="All">
          <el-option label="Pending" :value="0" />
          <el-option label="Processing" :value="1" />
          <el-option label="Resolved" :value="2" />
        </el-select>
      </el-form-item>
      <el-form-item label="Type">
        <el-select v-model="query.type" clearable placeholder="All">
          <el-option label="Large Transaction" value="large_tx" />
          <el-option label="Suspicious Activity" value="suspicious" />
          <el-option label="Sanctions Hit" value="sanctions" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary">Search</el-button>
      </el-form-item>
    </el-form>
    <el-table v-loading="loading" :data="events">
      <el-table-column prop="id" label="ID" />
      <el-table-column prop="customerName" label="Customer" />
      <el-table-column prop="type" label="Event Type" />
      <el-table-column prop="description" label="Description" show-overflow-tooltip />
      <el-table-column prop="amount" label="Amount" />
      <el-table-column prop="status" label="Status">
        <template #default="{ row }">
          <el-tag :type="row.status === 2 ? 'success' : row.status === 1 ? 'warning' : 'danger'">
            {{ ['Pending', 'Processing', 'Resolved'][row.status] }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="Actions">
        <template #default>
          <el-button type="primary" link>Review</el-button>
          <el-button type="warning" link>Escalate</el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-card>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';

const loading = ref(false);
const query = ref({ status: undefined as number | undefined, type: '' });
const events = ref([
  { id: 1, customerName: 'John Smith', type: 'Large Transaction', description: 'Single deposit exceeding $10,000 threshold', amount: '$50,000', status: 0 },
  { id: 2, customerName: 'Jane Doe', type: 'Suspicious Activity', description: 'Rapid deposit and withdrawal pattern detected', amount: '$25,000', status: 1 },
  { id: 3, customerName: 'Corp Trading Ltd', type: 'Sanctions Hit', description: 'Name matches OFAC SDN list entry', amount: '$100,000', status: 0 },
]);

const fetchData = () => { loading.value = true; setTimeout(() => { loading.value = false; }, 500); };
onMounted(fetchData);
</script>
