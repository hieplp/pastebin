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
      <button class="btn btn-sm btn-ghost">Edit</button>
      <button class="btn btn-sm btn-ghost">Delete</button>
    </div>
  </div>
</template>

<style scoped></style>
