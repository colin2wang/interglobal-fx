<template>
  <el-card>
    <template #header><div style="display:flex;justify-content:space-between;align-items:center"><span>Order List</span><ExportButton :api="orderApi.getList" /></div></template>
    <SearchForm v-model="query" @search="handleSearch" @reset="handleReset">
      <el-form-item label="Symbol"><el-input v-model="query.symbol" placeholder="Symbol" clearable /></el-form-item>
      <el-form-item label="Status"><el-select v-model="query.status" placeholder="Status" clearable><el-option label="Pending" value="pending" /><el-option label="Filled" value="filled" /><el-option label="Cancelled" value="cancelled" /></el-select></el-form-item>
    </SearchForm>
    <DataTable :data="data" :loading="loading" :total="total" v-model:page="page" v-model:limit="limit" @page-change="fetchData" show-actions :actions-width="120">
      <el-table-column prop="id" label="ID" width="120" />
      <el-table-column prop="accountNo" label="Account" width="120" />
      <el-table-column prop="symbol" label="Symbol" width="100" />
      <el-table-column prop="side" label="Side" width="80"><template #default="{row}"><el-tag :type="row.side==='buy'?'success':'danger'" size="small">{{row.side.toUpperCase()}}</el-tag></template></el-table-column>
      <el-table-column prop="type" label="Type" width="80" />
      <el-table-column prop="volume" label="Volume" width="80" />
      <el-table-column prop="status" label="Status" width="100"><template #default="{row}"><StatusTag :status="row.status" /></template></el-table-column>
      <el-table-column prop="openTime" label="Time" width="160" />
      <template #actions="{row}"><ActionButtons show-edit @edit="console.log(row)" /></template>
    </DataTable>
  </el-card>
</template>

<script setup lang="ts">
import SearchForm from '@/components/form/SearchForm.vue';
import DataTable from '@/components/table/DataTable.vue';
import StatusTag from '@/components/common/StatusTag.vue';
import ActionButtons from '@/components/common/ActionButtons.vue';
import ExportButton from '@/components/common/ExportButton.vue';
import { orderApi } from '@/api';
import { useTable } from '@/composables/useTable';

const { loading, data, total, page, limit, query, fetchData, handleSearch, handleReset } = useTable({ api: orderApi.getList });
</script>
