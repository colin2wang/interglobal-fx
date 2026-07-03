<template>
  <div class="header">
    <div class="left">
      <el-icon class="hamburger" @click="settingsStore.toggleSidebar"
        ><Fold v-if="sidebarOpened" /><Expand v-else
      /></el-icon>
    </div>
    <div class="right">
      <el-dropdown>
        <span class="user-info"
          ><el-avatar :size="30" /><span class="username">{{
            userInfo?.nickname || 'Admin'
          }}</span></span
        >
        <template #dropdown>
          <el-dropdown-menu
            ><el-dropdown-item @click="handleLogout">Logout</el-dropdown-item></el-dropdown-menu
          >
        </template>
      </el-dropdown>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue';
import { Fold, Expand } from '@element-plus/icons-vue';
import { useSettingsStore } from '@/store/settings';
import { useUserStore } from '@/store/user';

const settingsStore = useSettingsStore();
const userStore = useUserStore();
const sidebarOpened = computed(() => settingsStore.sidebar.opened);
const userInfo = computed(() => userStore.userInfo);
const handleLogout = async () => {
  await userStore.logout();
};
</script>

<style scoped>
.header {
  height: 50px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 16px;
  background: #fff;
  box-shadow: 0 1px 4px rgba(0, 21, 41, 0.08);
}
.left {
  display: flex;
  align-items: center;
}
.hamburger {
  font-size: 20px;
  cursor: pointer;
  margin-right: 16px;
}
.right {
  display: flex;
  align-items: center;
}
.user-info {
  display: flex;
  align-items: center;
  cursor: pointer;
}
.username {
  margin-left: 8px;
}
</style>
