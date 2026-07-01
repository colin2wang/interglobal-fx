declare namespace Common {
  interface TreeNode { key: string; title: string; children?: TreeNode[]; }
  interface SelectOption { label: string; value: string | number; disabled?: boolean; }
  interface KeyValue<T = string> { key: string; value: T; }
}
