<script lang="ts" setup>
import PasteHeaderName from '@/components/paste/PasteHeaderName.vue'

export interface PasteHeaderProps {
  pasteId: string
  noteName: string
  username: string
}

const {
  pasteId,
  noteName,
  username
} = defineProps<PasteHeaderProps>()
</script>

<template>
  <div class="flex justify-between items-center">
    <PasteHeaderName
      :paste-id="pasteId"
      :note-name="noteName"
      :username="username"
    />

    <div class="flex space-x-1">
      <a :href="`/edit-paste/:id`" class="btn btn-sm btn-ghost">Edit</a>
      <button class="btn btn-sm btn-ghost">Delete</button>
    </div>
  </div>
</template>

<style scoped></style>
