import { ref } from 'vue';

const cache = new Map<string, { label: string; value: string | number }[]>();

export function useDict(type: string) {
  const options = ref<{ label: string; value: string | number }[]>([]);
  if (cache.has(type)) {
    options.value = cache.get(type)!;
  } else {
    const mock: Record<string, any[]> = {
      order_status: [
        { label: 'Pending', value: 'pending' },
        { label: 'Filled', value: 'filled' },
      ],
      kyc_status: [
        { label: 'Pending', value: 'pending' },
        { label: 'Verified', value: 'verified' },
      ],
    };
    options.value = mock[type] || [];
    cache.set(type, options.value);
  }
  return { options };
}
