<template>
  <el-dialog v-model="visible" :title="title" width="400px">
    <p>{{ content }}</p>
    <template #footer>
      <el-button @click="visible = false">Cancel</el-button>
      <el-button
        type="primary"
        :loading="loading"
        @click="
          $emit('confirm');
          visible = false;
        "
        >Confirm</el-button
      >
    </template>
  </el-dialog>
</template>

<script setup lang="ts">
import { ref, watch } from 'vue';

const props = defineProps<{ modelValue: boolean; title?: string; content?: string }>();
const emit = defineEmits<{ (e: 'update:modelValue', v: boolean): void; (e: 'confirm'): void }>();

const loading = ref(false);
const visible = ref(props.modelValue);
watch(
  () => props.modelValue,
  (v) => (visible.value = v),
);
watch(visible, (v) => emit('update:modelValue', v));
</script>
