import { ref, reactive } from 'vue';
import { ElMessage } from 'element-plus';

export function useTable<T = any>(options: {
  api: (params: any) => Promise<any>;
  immediate?: boolean;
}) {
  const { api, immediate = true } = options;
  const loading = ref(false);
  const data = ref<T[]>([]) as any;
  const total = ref(0);
  const page = ref(1);
  const limit = ref(10);
  const query = reactive<Record<string, any>>({});

  const fetchData = async () => {
    loading.value = true;
    try {
      const res = await api({ page: page.value, pageSize: limit.value, ...query });
      data.value = res.data.list;
      total.value = res.data.total;
    } catch {
      ElMessage.error('Failed to fetch data');
    } finally {
      loading.value = false;
    }
  };

  const handleSearch = () => {
    page.value = 1;
    fetchData();
  };
  const handleReset = () => {
    Object.keys(query).forEach((k) => {
      query[k] = undefined;
    });
    handleSearch();
  };

  if (immediate) fetchData();
  return { loading, data, total, page, limit, query, fetchData, handleSearch, handleReset };
}
