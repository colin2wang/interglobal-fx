<template>
  <el-card title="KYC Review">
    <el-form :model="form" label-width="120px">
      <el-form-item label="Status"
        ><el-radio-group v-model="form.status"
          ><el-radio value="verified">Approve</el-radio
          ><el-radio value="rejected">Reject</el-radio></el-radio-group
        ></el-form-item
      >
      <el-form-item label="Remark"
        ><el-input v-model="form.remark" type="textarea" :rows="3"
      /></el-form-item>
      <el-form-item
        ><el-button type="primary" @click="handleSubmit">Submit</el-button></el-form-item
      >
    </el-form>
  </el-card>
</template>

<script setup lang="ts">
import { reactive } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { ElMessage } from 'element-plus';
import { customerApi } from '@/api';
const route = useRoute();
const router = useRouter();
const form = reactive({ status: 'verified', remark: '' });
const handleSubmit = async () => {
  await customerApi.reviewKyc(Number(route.params.id), form);
  ElMessage.success('Reviewed');
  router.push('/customer');
};
</script>
