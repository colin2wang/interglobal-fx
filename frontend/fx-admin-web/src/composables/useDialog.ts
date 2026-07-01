import { ref } from 'vue';

export function useDialog() {
  const visible = ref(false);
  const title = ref('');
  const mode = ref<'create' | 'edit'>('create');
  const formData = ref<any>({});

  const openDialog = (data?: any) => {
    mode.value = data ? 'edit' : 'create';
    title.value = data ? 'Edit' : 'Create';
    formData.value = data ? { ...data } : {};
    visible.value = true;
  };
  const closeDialog = () => { visible.value = false; formData.value = {}; };
  return { visible, title, mode, formData, openDialog, closeDialog };
}
