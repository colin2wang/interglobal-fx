<template>
  <el-card :title="isEdit ? 'Edit User' : 'Create User'">
    <el-form ref="formRef" :model="form" :rules="rules" label-width="120px">
      <el-form-item label="Username" prop="username"
        ><el-input v-model="form.username" :disabled="isEdit"
      /></el-form-item>
      <el-form-item label="Nickname" prop="nickname"
        ><el-input v-model="form.nickname"
      /></el-form-item>
      <el-form-item label="Email" prop="email"><el-input v-model="form.email" /></el-form-item>
      <el-form-item label="Phone"><el-input v-model="form.phone" /></el-form-item>
      <el-form-item label="Status"
        ><el-switch v-model="form.status" :active-value="1" :inactive-value="0"
      /></el-form-item>
      <el-form-item
        ><el-button type="primary" @click="handleSubmit">Save</el-button
        ><el-button @click="$router.back()">Cancel</el-button></el-form-item
      >
    </el-form>
  </el-card>
</template>

<script setup lang="ts">
import { ref, reactive, computed, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { ElMessage } from 'element-plus';
import { userApi } from '@/api';
const route = useRoute();
const router = useRouter();
const formRef = ref();
const isEdit = computed(() => !!route.params.id);
const form = reactive({ username: '', nickname: '', email: '', phone: '', status: 1 });
const rules = {
  username: [{ required: true, message: 'Required', trigger: 'blur' }],
  nickname: [{ required: true, message: 'Required', trigger: 'blur' }],
  email: [{ required: true, message: 'Required', trigger: 'blur' }],
};
const handleSubmit = async () => {
  await formRef.value?.validate();
  if (isEdit.value) await userApi.update(Number(route.params.id), form);
  else await userApi.create(form);
  ElMessage.success('Saved');
  router.push('/system/user');
};
onMounted(async () => {
  if (isEdit.value) {
    const res = await userApi.getList({ id: route.params.id });
    Object.assign(form, res.data.list[0]);
  }
});
</script>
