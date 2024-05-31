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
    <div class="flex flex-col space-y-1">
      <PasteHeader :paste="paste" />
      <p class="text-gray-400 line-clamp-3">
        {{ paste.description }}
      </p>
    </div>

    <PasteContents :contents="paste.contents" class="max-h-60 overflow-y-hidden" />
  </div>
</template>

<style scoped></style>
