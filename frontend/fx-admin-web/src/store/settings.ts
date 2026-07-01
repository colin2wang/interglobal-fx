import { defineStore } from 'pinia';

interface SettingsState {
  sidebar: { opened: boolean; withoutAnimation: boolean };
  device: 'desktop' | 'mobile';
  theme: string;
}

export const useSettingsStore = defineStore('settings', {
  state: (): SettingsState => ({ sidebar: { opened: true, withoutAnimation: false }, device: 'desktop', theme: 'light' }),
  actions: {
    toggleSidebar() { this.sidebar.opened = !this.sidebar.opened; },
    closeSidebar() { this.sidebar.opened = false; },
    toggleDevice(d: 'desktop' | 'mobile') { this.device = d; },
  },
});
