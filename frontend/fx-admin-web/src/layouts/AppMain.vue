<template>
  <div class="app-main">
    <router-view v-slot="{ Component }">
      <transition name="fade-transform" mode="out-in">
        <keep-alive><component :is="Component" /></keep-alive>
      </transition>
    </router-view>
  </div>
</template>

<script setup lang="ts"></script>

<style scoped>
.app-main { flex: 1; padding: 16px; overflow-y: auto; background-color: #f0f2f5; }
</style>
