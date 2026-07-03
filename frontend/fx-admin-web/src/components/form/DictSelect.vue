<template>
  <el-select v-bind="$attrs">
    <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value" />
  </el-select>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
const props = defineProps<{ dictType: string }>();
const options = ref<{ label: string; value: string | number }[]>([]);
onMounted(() => {
  const data: Record<string, any[]> = {
    order_status: [
      { label: 'Pending', value: 'pending' },
      { label: 'Filled', value: 'filled' },
    ],
    kyc_status: [
      { label: 'Pending', value: 'pending' },
      { label: 'Verified', value: 'verified' },
    ],
  };
  options.value = data[props.dictType] || [];
});
</script>
