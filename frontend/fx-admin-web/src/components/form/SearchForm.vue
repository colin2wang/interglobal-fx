<template>
  <el-form :model="form" inline>
    <slot :form="form" />
    <el-form-item>
      <el-button type="primary" @click="$emit('search')">Search</el-button>
      <el-button @click="handleReset">Reset</el-button>
    </el-form-item>
  </el-form>
</template>

<script setup lang="ts">
import { reactive } from 'vue';
const props = defineProps<{ modelValue: Record<string, any> }>();
const emit = defineEmits<{ (e: 'update:modelValue', v: Record<string, any>): void; (e: 'search'): void; (e: 'reset'): void }>();
const form = reactive({ ...props.modelValue });
const handleReset = () => { Object.keys(form).forEach((k) => { (form as any)[k] = undefined; }); emit('update:modelValue', { ...form }); emit('reset'); };
</script>
