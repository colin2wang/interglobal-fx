<template>
  <div class="error-page">
    <h1>403</h1>
    <p>Access Denied</p>
    <el-button type="primary" @click="$router.push('/')">Back to Home</el-button>
  </div>
</template>

<script setup lang="ts"></script>

<style scoped>
.error-page {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100vh;
}
h1 {
  font-size: 120px;
  margin: 0;
  color: #e6a23c;
}
p {
  font-size: 24px;
  margin-bottom: 24px;
}
</style>
