<script lang="ts" setup>
import MainLayout from '@/components/layouts/MainLayout.vue'
import OverviewPaste from '@/components/paste/OverviewPaste.vue'
import PasteService from '@/services/paste.service'
import { onMounted, reactive, ref } from 'vue'
import type GetOwnPastesRequest from '@/types/payload/paste/get-own.req'
import type PasteResponse from '@/types/payload/paste/paste.res'

const params = reactive<GetOwnPastesRequest>({
  pageNo: 0,
  pageSize: 10
})

const pastes = ref<PasteResponse[]>([])
const total = ref(0)

const loadOwnPastes = () => {
  PasteService.getOwnPastes(params)
    .then((data) => {
      pastes.value = data.list
      total.value = data.total
    })
    .catch((error) => {
      console.log(error)
    })
}

onMounted(() => {
  loadOwnPastes()
})

</script>

<template>
  <MainLayout>
    <OverviewPaste
      v-for="paste in pastes"
      :key="paste.pasteId"
      :paste="paste"
    />
  </MainLayout>
</template>

<style scoped></style>
