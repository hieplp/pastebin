<script lang="ts" setup>
import MainLayout from '@/components/layouts/MainLayout.vue'
import { reactive } from 'vue'
import PrivacyConstants from '@/constants/PrivacyConstants'
import ExpiryConstants from '@/constants/ExpiryConstants'
import SavePasteForm from '@/components/paste/SavePasteForm.vue'

const form = reactive({
  title: '',
  content: '',
  expiry: ExpiryConstants.Options[0].id,
  privacy: PrivacyConstants.Options[0].id
})

const formErrors = reactive({
  title: '',
  content: ''
})

const formErrorMessages = reactive({
  title: '',
  content: ''
})

function handleCreate() {
  console.log('form', form)
}
</script>

<template>
  <MainLayout>
    <div class="p-6 space-y-5">
      <div class="text-sm breadcrumbs">
        <ul>
          <li><a href="/">Home</a></li>
          <li><a href="/own-pastes">Pastes</a></li>
          <li>Add Paste</li>
        </ul>
      </div>

      <div class="space-y-4 md:space-y-6">
        <SavePasteForm v-model="form" />
        <button class="btn btn-active btn-ghost w-full" @click="handleCreate">Create</button>
      </div>
    </div>
  </MainLayout>
</template>

<style scoped></style>
