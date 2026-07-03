<template>
  <el-date-picker
    v-model="dateRange"
    type="daterange"
    range-separator="To"
    start-placeholder="Start"
    end-placeholder="End"
    value-format="YYYY-MM-DD"
    @change="(v: any) => $emit('update:modelValue', v)"
  />
</template>

<script setup lang="ts">
import { ref } from 'vue';
defineEmits<{ (e: 'update:modelValue', v: [string, string] | null): void }>();
const dateRange = ref<[string, string] | null>(null);
</script>
