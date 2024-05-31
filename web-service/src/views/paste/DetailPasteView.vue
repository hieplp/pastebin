<script lang="ts" setup>
import MainLayout from '@/components/layouts/MainLayout.vue'
import type PasteType from '@/types/paste.type'
import PasteHeader from '@/components/paste/PasteHeader.vue'
import PasteContents from '@/components/paste/PasteContents.vue'

const paste: PasteType = {
  id: 2,
  name: 'note_name',
  description:
    'lorem ipsum dolor sit amet, consectetur adipiscing elit.' +
    'lorem ipsum dolor sit amet, consectetur adipiscing elit.' +
    'lorem ipsum dolor sit amet, consectetur adipiscing elit.' +
    'lorem ipsum dolor sit amet, consectetur adipiscing elit.' +
    'lorem ipsum dolor sit amet, consectetur adipiscing elit.' +
    'lorem ipsum dolor sit amet, consectetur adipiscing elit.' +
    'lorem ipsum dolor sit amet, consectetur adipiscing elit.' +
    'lorem ipsum dolor sit amet, consectetur adipiscing elit.',
  contents: [
    'lorem ipsum dolor sit amet, consectetur adipiscing elit.lorem ipsum dolor sit amet, consectetur adipiscing elit,lorem ipsum dolor sit amet, consectetur adipiscing elit',
    'lorem ipsum dolor sit amet, consectetur adipiscing elit.',
    'lorem ipsum dolor sit amet, consectetur adipiscing elit.',
    'lorem ipsum dolor sit amet, consectetur adipiscing elit.',
    'lorem ipsum dolor sit amet, consectetur adipiscing elit.',
    'lorem ipsum dolor sit amet, consectetur adipiscing elit.',
    'lorem ipsum dolor sit amet, consectetur adipiscing elit.',
    'lorem ipsum dolor sit amet, consectetur adipiscing elit.lorem ipsum dolor sit amet, consectetur adipiscing elit,lorem ipsum dolor sit amet, consectetur adipiscing elit',
    'lorem ipsum dolor sit amet, consectetur adipiscing elit.',
    'lorem ipsum dolor sit amet, consectetur adipiscing elit.',
    'lorem ipsum dolor sit amet, consectetur adipiscing elit.',
    'lorem ipsum dolor sit amet, consectetur adipiscing elit.',
    'lorem ipsum dolor sit amet, consectetur adipiscing elit.',
    'lorem ipsum dolor sit amet, consectetur adipiscing elit.',
    'lorem ipsum dolor sit amet, consectetur adipiscing elit.lorem ipsum dolor sit amet, consectetur adipiscing elit,lorem ipsum dolor sit amet, consectetur adipiscing elit',
    'lorem ipsum dolor sit amet, consectetur adipiscing elit.',
    'lorem ipsum dolor sit amet, consectetur adipiscing elit.',
    'lorem ipsum dolor sit amet, consectetur adipiscing elit.',
    'lorem ipsum dolor sit amet, consectetur adipiscing elit.',
    'lorem ipsum dolor sit amet, consectetur adipiscing elit.',
    'lorem ipsum dolor sit amet, consectetur adipiscing elit.',
    'lorem ipsum dolor sit amet, consectetur adipiscing elit.'
  ]
}
</script>

<template>
  <MainLayout>
    <div class="p-6 space-y-5">
      <div class="flex flex-col space-y-1">
        <PasteHeader :paste="paste" />
        <p class="text-gray-400 max-h-32 scroller-y">
          {{ paste.description }}
        </p>
      </div>

      <PasteContents :contents="paste.contents" class="" />
    </div>
  </MainLayout>
</template>

<style scoped></style>
