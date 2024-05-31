<script lang="ts" setup>
export type PasteContentsProps = {
  contents: string[]
  className?: string
}

const { contents } = defineProps<PasteContentsProps>()
</script>

<template>
  <ul class="border rounded p-2 border-gray-700 overflow-y-hidden scroller-x">
    <li v-for="(content, index) in contents" :key="content" class="flex items-center max-w-full">
      <span class="text-end text-sm mx-5">
        {{ index + 1 }}
      </span>

      <code class="text-white whitespace-nowrap">
        {{ content }}
      </code>
    </li>
  </ul>
</template>

<style scoped></style>
