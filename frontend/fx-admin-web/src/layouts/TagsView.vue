<template>
  <div class="tags-view">
    <el-tag
      v-for="tag in tags"
      :key="tag.path"
      :closable="tag.path !== '/dashboard'"
      :class="{ active: tag.path === route.path }"
      @click="router.push(tag.path)"
      @close="handleClose(tag)"
      >{{ tag.title }}</el-tag
    >
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
const route = useRoute();
const router = useRouter();
const tags = ref([{ path: '/dashboard', title: 'Dashboard' }]);
const handleClose = (tag: any) => {
  const i = tags.value.findIndex((t) => t.path === tag.path);
  if (i > -1) tags.value.splice(i, 1);
};
</script>

<style scoped>
.tags-view {
  display: flex;
  align-items: center;
  gap: 4px;
  padding: 4px 16px;
  background: #fff;
  border-bottom: 1px solid #eee;
}
.el-tag {
  cursor: pointer;
}
.el-tag.active {
  background-color: #409eff;
  color: #fff;
}
</style>
