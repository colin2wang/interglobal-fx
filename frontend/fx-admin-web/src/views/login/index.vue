<template>
  <div class="login-container">
    <el-card class="login-card">
      <h2 class="title">FX Admin Panel</h2>
      <el-form ref="formRef" :model="form" :rules="rules" @submit.prevent="handleLogin">
        <el-form-item prop="username"><el-input v-model="form.username" placeholder="Username" :prefix-icon="User" /></el-form-item>
        <el-form-item prop="password"><el-input v-model="form.password" type="password" placeholder="Password" :prefix-icon="Lock" show-password /></el-form-item>
        <el-form-item><el-button type="primary" :loading="loading" native-type="submit" style="width:100%">Login</el-button></el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue';
import { useRouter } from 'vue-router';
import { ElMessage } from 'element-plus';
import { User, Lock } from '@element-plus/icons-vue';
import { useUserStore } from '@/store/user';

const router = useRouter();
const userStore = useUserStore();
const formRef = ref();
const loading = ref(false);
const form = reactive({ username: '', password: '' });
const rules = { username: [{ required: true, message: 'Required', trigger: 'blur' }], password: [{ required: true, message: 'Required', trigger: 'blur' }] };

const handleLogin = async () => {
  await formRef.value?.validate();
  loading.value = true;
  try { await userStore.login(form.username, form.password); ElMessage.success('Login successful'); router.push('/'); }
  catch { ElMessage.error('Login failed'); } finally { loading.value = false; }
};
</script>

<style scoped>
.login-container { display: flex; justify-content: center; align-items: center; height: 100vh; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); }
.login-card { width: 400px; }
.title { text-align: center; margin-bottom: 30px; }
</style>
