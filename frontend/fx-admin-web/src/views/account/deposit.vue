<template>
  <el-card title="Deposit">
    <el-form :model="form" label-width="120px">
      <el-form-item label="Account No"><el-input v-model="form.accountNo" /></el-form-item>
      <el-form-item label="Amount"><el-input-number v-model="form.amount" :min="1" /></el-form-item>
      <el-form-item label="Method"
        ><el-select v-model="form.method"
          ><el-option label="Bank Transfer" value="bank" /><el-option
            label="Cryptocurrency"
            value="crypto" /></el-select
      ></el-form-item>
      <el-form-item
        ><el-button type="primary" @click="handleSubmit">Submit</el-button></el-form-item
      >
    </el-form>
  </el-card>
</template>

<script setup lang="ts">
import { reactive } from 'vue';
import { ElMessage } from 'element-plus';
import request from '@/api/axios';
const form = reactive({ accountNo: '', amount: 0, method: 'bank' });
const handleSubmit = async () => {
  await request.post('/admin/accounts/deposit', form);
  ElMessage.success('Deposit submitted');
};
</script>
