import i18n from 'i18next';
import { initReactI18next } from 'react-i18next';
import zhCN from './zh-CN';
import enUS from './en-US';

i18n.use(initReactI18next).init({
  resources: { 'zh-CN': { translation: zhCN }, 'en-US': { translation: enUS } },
  lng: localStorage.getItem('locale') || 'zh-CN',
  fallbackLng: 'en-US',
  interpolation: { escapeValue: false },
});

export default i18n;
