<template>
  <div class="app-wrapper">
    <Sidebar />
    <div class="main-container">
      <Header />
      <TagsView />
      <AppMain />
    </div>
  </div>
</template>

<script setup lang="ts">
import Sidebar from './Sidebar.vue';
import Header from './Header.vue';
import TagsView from './TagsView.vue';
import AppMain from './AppMain.vue';
</script>

<style scoped>
.app-wrapper {
  display: flex;
  height: 100vh;
}
.main-container {
  flex: 1;
  display: flex;
  flex-direction: column;
  overflow: hidden;
}
</style>
