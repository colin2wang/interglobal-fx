<template>
  <el-card title="Customer Detail">
    <el-descriptions :column="2" border>
      <el-descriptions-item label="ID">{{ customer.id }}</el-descriptions-item>
      <el-descriptions-item label="Username">{{ customer.username }}</el-descriptions-item>
      <el-descriptions-item label="Email">{{ customer.email }}</el-descriptions-item>
      <el-descriptions-item label="Phone">{{ customer.phone }}</el-descriptions-item>
      <el-descriptions-item label="KYC"
        ><StatusTag :status="customer.kycStatus || 'pending'"
      /></el-descriptions-item>
    </el-descriptions>
  </el-card>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import StatusTag from '@/components/common/StatusTag.vue';
import { customerApi } from '@/api';
const route = useRoute();
const customer = ref<any>({});
onMounted(async () => {
  const res = await customerApi.getDetail(Number(route.params.id));
  customer.value = res.data;
});
</script>
