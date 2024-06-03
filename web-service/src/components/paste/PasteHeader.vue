<script lang="ts" setup>
import PasteHeaderName from '@/components/paste/PasteHeaderName.vue'

export interface PasteHeaderProps {
  noteName: string
  username: string
}
</script>

<template>
  <div class="flex justify-between items-center">
    <PasteHeaderName note-name="note_name" username="hieplp" />

    <div class="flex space-x-1">
      <a :href="`/edit-paste/:id`" class="btn btn-sm btn-ghost">Edit</a>
      <button class="btn btn-sm btn-ghost">Delete</button>
    </div>
  </div>
</template>

<style scoped></style>
