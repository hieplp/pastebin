<script lang="ts" setup>
export interface PasteHeaderProps {
  username: string
  noteName: string
}

const { username, noteName } = defineProps<PasteHeaderProps>()
</script>

<template>
  <div class="space-x-2 flex text-white">
    <a class="font-bold hover:underline" href="">
      {{ username }}
    </a>
    <p class="text-gray-500">/</p>
    <a class="font-bold hover:underline" href="">
      {{ noteName }}
    </a>
  </div>
</template>

<style scoped></style>
