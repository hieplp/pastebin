<script lang="ts" setup>
import MainLayout from '@/components/layouts/MainLayout.vue'
import type PasteType from '@/types/paste.type'
import OverviewPaste from '@/components/paste/OverviewPaste.vue'

const pastes: PasteType[] = [
  {
    id: 1,
    name: 'note_name',
    description: 'lorem ipsum dolor sit amet, consectetur adipiscing elit.',
    contents: [
      'lorem ipsum dolor sit amet, consectetur adipiscing elit.lorem ipsum dolor sit amet, consectetur adipiscing elit,lorem ipsum dolor sit amet, consectetur adipiscing elit',
      'lorem ipsum dolor sit amet, consectetur adipiscing elit.',
      'lorem ipsum dolor sit amet, consectetur adipiscing elit.',
      'lorem ipsum dolor sit amet, consectetur adipiscing elit.',
      'lorem ipsum dolor sit amet, consectetur adipiscing elit.',
      'lorem ipsum dolor sit amet, consectetur adipiscing elit.',
      'lorem ipsum dolor sit amet, consectetur adipiscing elit.',
      'lorem ipsum dolor sit amet, consectetur adipiscing elit.',
      'lorem ipsum dolor sit amet, consectetur adipiscing elit.',
      'lorem ipsum dolor sit amet, consectetur adipiscing elit.',
      'lorem ipsum dolor sit amet, consectetur adipiscing elit.',
      'lorem ipsum dolor sit amet, consectetur adipiscing elit.',
      'lorem ipsum dolor sit amet, consectetur adipiscing elit.',
      'lorem ipsum dolor sit amet, consectetur adipiscing elit.'
    ]
  },
  {
    id: 2,
    name: 'note_name',
    description: 'lorem ipsum dolor sit amet, consectetur adipiscing elit.',
    contents: [
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
]
</script>

<template>
  <MainLayout>
    <OverviewPaste v-for="paste in pastes" :key="paste.id" :paste="paste" />
  </MainLayout>
</template>

<style scoped></style>
