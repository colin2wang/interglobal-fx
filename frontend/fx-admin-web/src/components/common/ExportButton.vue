<template>
  <el-button :loading="loading" @click="handleExport"><el-icon><Download /></el-icon> Export</el-button>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import { Download } from '@element-plus/icons-vue';
import { ElMessage } from 'element-plus';

const props = defineProps<{ api: (params: any) => Promise<any>; params?: Record<string, any> }>();
const loading = ref(false);
const handleExport = async () => {
  loading.value = true;
  try { await props.api(props.params || {}); ElMessage.success('Export successful'); }
  catch { ElMessage.error('Export failed'); } finally { loading.value = false; }
};
</script>
