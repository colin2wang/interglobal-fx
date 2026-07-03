<template>
  <el-card>
    <template #header>
      <div style="display: flex; justify-content: space-between; align-items: center">
        <span>Customer Follow-up</span>
        <el-button type="primary">Add Follow-up</el-button>
      </div>
    </template>
    <el-form :inline="true" :model="query" class="search-form">
      <el-form-item label="Customer">
        <el-input v-model="query.customerName" placeholder="Search customer" clearable />
      </el-form-item>
      <el-form-item label="Status">
        <el-select v-model="query.status" clearable placeholder="All">
          <el-option label="Pending" value="pending" />
          <el-option label="Completed" value="completed" />
          <el-option label="Overdue" value="overdue" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary">Search</el-button>
      </el-form-item>
    </el-form>
    <el-table v-loading="loading" :data="followUps">
      <el-table-column prop="id" label="ID" />
      <el-table-column prop="customerName" label="Customer" />
      <el-table-column prop="type" label="Type" />
      <el-table-column prop="content" label="Content" show-overflow-tooltip />
      <el-table-column prop="assignee" label="Assignee" />
      <el-table-column prop="dueDate" label="Due Date" />
      <el-table-column prop="status" label="Status">
        <template #default="{ row }">
          <el-tag :type="row.status === 'completed' ? 'success' : row.status === 'overdue' ? 'danger' : 'warning'">
            {{ row.status }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="Actions">
        <template #default>
          <el-button type="primary" link>View</el-button>
          <el-button type="success" link>Complete</el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-card>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';

const loading = ref(false);
const query = ref({ customerName: '', status: '' });
const followUps = ref([
  { id: 1, customerName: 'John Smith', type: 'KYC Follow-up', content: 'Missing proof of address document', assignee: 'Alice', dueDate: '2024-01-20', status: 'pending' },
  { id: 2, customerName: 'Jane Doe', type: 'Deposit Issue', content: 'Wire transfer pending for 3 days', assignee: 'Bob', dueDate: '2024-01-18', status: 'overdue' },
  { id: 3, customerName: 'Corp Trading Ltd', type: 'Account Review', content: 'Annual compliance review completed', assignee: 'Alice', dueDate: '2024-01-15', status: 'completed' },
]);

onMounted(() => { loading.value = true; setTimeout(() => { loading.value = false; }, 500); });
</script>
