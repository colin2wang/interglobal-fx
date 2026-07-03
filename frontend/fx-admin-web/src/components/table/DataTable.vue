<template>
  <div>
    <el-table v-loading="loading" :data="data" border stripe>
      <el-table-column v-if="showSelection" type="selection" width="55" />
      <slot />
      <el-table-column v-if="showActions" label="Actions" :width="actionsWidth" fixed="right">
        <template #default="scope"><slot name="actions" :row="scope.row" /></template>
      </el-table-column>
    </el-table>
    <div style="margin-top: 16px; display: flex; justify-content: flex-end">
      <Pagination
        :total="total"
        :page="page"
        :limit="limit"
        @update:page="$emit('update:page', $event)"
        @update:limit="$emit('update:limit', $event)"
        @change="$emit('page-change')"
      />
    </div>
  </div>
</template>

<script setup lang="ts">
import Pagination from '@/components/common/Pagination.vue';
defineProps<{
  data: any[];
  loading?: boolean;
  total: number;
  page?: number;
  limit?: number;
  showSelection?: boolean;
  showActions?: boolean;
  actionsWidth?: number;
}>();
defineEmits<{
  (e: 'update:page', v: number): void;
  (e: 'update:limit', v: number): void;
  (e: 'page-change'): void;
}>();
</script>
