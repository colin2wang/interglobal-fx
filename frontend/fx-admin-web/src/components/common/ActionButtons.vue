<template>
  <div class="action-buttons">
    <el-button v-if="showEdit" type="primary" link @click="$emit('edit')">Edit</el-button>
    <el-button v-if="showDelete" type="danger" link @click="$emit('delete')">Delete</el-button>
    <slot />
  </div>
</template>

<script setup lang="ts">
defineProps<{ showEdit?: boolean; showDelete?: boolean }>();
defineEmits<{ (e: 'edit'): void; (e: 'delete'): void }>();
</script>
