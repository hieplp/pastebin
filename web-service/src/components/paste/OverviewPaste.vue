<script lang="ts" setup>
import type PasteType from '@/types/paste.type'
import PasteContents from '@/components/paste/PasteContents.vue'
import PasteHeader from '@/components/paste/PasteHeader.vue'

type OverviewPasteProps = {
  paste: PasteType
}

const { paste } = defineProps<OverviewPasteProps>()
</script>

<template>
  <div class="p-6 space-y-3">
    <div class="flex flex-col">
      <div class="flex justify-between items-center">
        <PasteHeader note-name="note_name" username="hieplp" />

        <div class="flex space-x-1">
          <button class="btn btn-sm btn-ghost">Edit</button>
          <button class="btn btn-sm btn-ghost">Delete</button>
        </div>
      </div>

      <p class="text-gray-400 line-clamp-3">
        {{ paste.description }}
      </p>
    </div>

    <PasteContents :contents="paste.contents" class="max-h-60" />
  </div>
</template>

<style scoped></style>
