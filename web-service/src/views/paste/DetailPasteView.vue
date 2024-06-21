<script lang="ts" setup>
import MainLayout from '@/components/layouts/MainLayout.vue'
import PasteHeader from '@/components/paste/PasteHeader.vue'
import PasteContents from '@/components/paste/PasteContents.vue'
import PasteService from '@/services/paste.service'
import { useRoute, useRouter } from 'vue-router'
import { computed, onMounted } from 'vue'
import { usePasteStore } from '@/stores/paste.store'
import { useToast } from 'vue-toastification'

const router = useRouter()

const route = useRoute()

const toast = useToast()

const pasteStore = usePasteStore()

const {
  pasteId
} = route.params

const paste = computed(() => pasteStore.paste)


onMounted(() => {
  loadPaste()
})

const loadPaste = () => {
  console.log('load paste')
  PasteService.getOwnPasteById(pasteId + '')
    .then((data) => {
      pasteStore.setPaste(data)
    })
    .catch(() => {
      toast.error('Failed to load paste')
    })
}

</script>

<template>
  <MainLayout>
    <div class="p-6 space-y-5">
      <div class="flex flex-col space-y-1">
        <PasteHeader
          :paste-id="paste.pasteId"
          :note-name="paste.alias"
          username="hieplp"
        />
        <p class="text-gray-400 max-h-32 scroller-y">
          {{ paste.title }}
        </p>
      </div>

      <PasteContents :content="paste.content" class="" />
    </div>
  </MainLayout>
</template>

<style scoped></style>
