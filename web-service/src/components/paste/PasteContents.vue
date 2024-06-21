<script lang="ts" setup>
import { computed } from 'vue'

export type PasteContentsProps = {
  className?: string
  content: string
}

const { content } = defineProps<PasteContentsProps>()

const contents = computed(() => content?.split('\n'))
</script>

<template>
  <ul class="border rounded p-2 border-gray-700 scroller-x">
    <li v-for="(content, index) in contents" :key="content" class="flex items-center max-w-full">
      <p class="text-end text-sm mx-5 min-w-5 max-w-5">
        {{ index + 1 }}
      </p>

      <code class="text-white whitespace-nowrap">
        {{ content }}
      </code>
    </li>
  </ul>
</template>

<style scoped></style>
