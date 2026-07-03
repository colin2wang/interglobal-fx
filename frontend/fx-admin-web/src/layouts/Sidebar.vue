<template>
  <div class="sidebar" :class="{ collapsed: !sidebarOpened }">
    <div class="logo"><span v-if="sidebarOpened">FX Admin</span><span v-else>FX</span></div>
    <el-menu
      :default-active="activeMenu"
      :collapse="!sidebarOpened"
      router
      background-color="#001529"
      text-color="#ffffffa6"
      active-text-color="#fff"
    >
      <el-menu-item index="/dashboard"
        ><el-icon><Odometer /></el-icon><template #title>Dashboard</template></el-menu-item
      >
      <el-menu-item index="/order"
        ><el-icon><Document /></el-icon><template #title>Orders</template></el-menu-item
      >
      <el-menu-item index="/position"
        ><el-icon><List /></el-icon><template #title>Positions</template></el-menu-item
      >
      <el-menu-item index="/account"
        ><el-icon><Wallet /></el-icon><template #title>Accounts</template></el-menu-item
      >
      <el-menu-item index="/customer"
        ><el-icon><User /></el-icon><template #title>Customers</template></el-menu-item
      >
      <el-menu-item index="/risk"
        ><el-icon><Warning /></el-icon><template #title>Risk</template></el-menu-item
      >
      <el-menu-item index="/system/user"
        ><el-icon><Setting /></el-icon><template #title>System</template></el-menu-item
      >
    </el-menu>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue';
import { useRoute } from 'vue-router';
import { useSettingsStore } from '@/store/settings';
import { Odometer, Document, List, Wallet, User, Warning, Setting } from '@element-plus/icons-vue';

const route = useRoute();
const settingsStore = useSettingsStore();
const sidebarOpened = computed(() => settingsStore.sidebar.opened);
const activeMenu = computed(() => route.path);
</script>

<style scoped>
.sidebar {
  width: 210px;
  height: 100vh;
  background-color: #001529;
  transition: width 0.3s;
}
.sidebar.collapsed {
  width: 64px;
}
.logo {
  height: 50px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #fff;
  font-size: 20px;
  font-weight: bold;
}
</style>
