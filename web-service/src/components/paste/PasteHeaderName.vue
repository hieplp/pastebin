<script lang="ts" setup>
import { computed } from 'vue'

export interface PasteHeaderProps {
  pasteId: string
  username: string
  noteName: string
}

const { username, noteName, pasteId } = defineProps<PasteHeaderProps>()

const pasteUrl = computed(() => `/pastes/${pasteId}`)

</script>

<template>
  <div class="space-x-2 flex text-white">
    <a class="font-bold hover:underline" href="">
      {{ username }}
    </a>
    <p class="text-gray-500">/</p>
    <a class="font-bold hover:underline" :href="pasteUrl">
      {{ noteName }}
    </a>
  </div>
</template>

<style scoped></style>
