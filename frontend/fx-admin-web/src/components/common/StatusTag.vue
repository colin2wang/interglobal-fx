<template>
  <el-tag :type="tagType" size="small">{{ text }}</el-tag>
</template>

<script setup lang="ts">
import { computed } from 'vue';

const props = defineProps<{ status: number | string }>();
const map: Record<string | number, { text: string; type: string }> = {
  0: { text: 'Inactive', type: 'danger' }, 1: { text: 'Active', type: 'success' },
  pending: { text: 'Pending', type: 'warning' }, verified: { text: 'Verified', type: 'success' },
  rejected: { text: 'Rejected', type: 'danger' }, filled: { text: 'Filled', type: 'success' },
  cancelled: { text: 'Cancelled', type: 'info' },
};
const cfg = computed(() => map[props.status] || { text: String(props.status), type: 'info' });
const text = computed(() => cfg.value.text);
const tagType = computed(() => cfg.value.type as any);
</script>
