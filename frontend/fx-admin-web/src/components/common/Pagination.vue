<template>
  <el-pagination
    v-model:current-page="currentPage"
    v-model:page-size="pageSize"
    :page-sizes="[10, 20, 50, 100]"
    :total="total"
    layout="total, sizes, prev, pager, next, jumper"
    @size-change="handleChange"
    @current-change="handleChange"
  />
</template>

<script setup lang="ts">
import { computed } from 'vue';

const props = defineProps<{ total: number; page?: number; limit?: number }>();
const emit = defineEmits<{
  (e: 'update:page', v: number): void;
  (e: 'update:limit', v: number): void;
  (e: 'change'): void;
}>();

const currentPage = computed({ get: () => props.page || 1, set: (v) => emit('update:page', v) });
const pageSize = computed({ get: () => props.limit || 10, set: (v) => emit('update:limit', v) });
const handleChange = () => emit('change');
</script>
