<script lang="ts" setup>
import MainLayout from '@/components/layouts/MainLayout.vue'
import { reactive, ref } from 'vue'
import PrivacyConstants from '@/constants/PrivacyConstants'
import ExpiryConstants from '@/constants/ExpiryConstants'
import SavePasteForm from '@/components/paste/SavePasteForm.vue'
import { useToast } from 'vue-toastification'
import { useRouter } from 'vue-router'
import PasteService from '@/services/paste.service'
import ErrorCode from '@/constants/ErrorCode'

// ----------------------------------------
const form = reactive({
  alias: '',
  title: '',
  content: '',
  expiry: ExpiryConstants.Options[0].id,
  privacy: PrivacyConstants.Options[0].id
})

const formErrors = reactive({
  alias: false,
  title: false,
  content: false
})

const formErrorMessages = reactive({
  alias: '',
  title: '',
  content: ''
})

const loading = ref<boolean>(false)

// ----------------------------------------

const toast = useToast()

const router = useRouter()

// ----------------------------------------
function handleCreate() {
  if (!validateForm()) {
    return
  }

  loading.value = true
  PasteService.create({
    alias: form.alias,
    title: form.title,
    content: form.content,
    // expiry: form.expiry,
    privacy: form.privacy
  })
    .then(() => {
      toast.success('Paste was created successfully')
      router.push('/own-pastes')
    })
    .catch((error) => {
      switch (error) {
        case ErrorCode.DUPLICATED:
          formErrors.alias = true
          formErrorMessages.alias = 'Alias is duplicated'
          break
      }
    })
    .finally(() => {
      loading.value = false
    })
}

const validateForm = () => {
  resetError()

  if (!form.title || form.title.trim().length === 0) {
    formErrors.title = true
    formErrorMessages.title = 'Title is required'
  }

  if (!form.content || form.content.trim().length === 0) {
    formErrors.content = true
    formErrorMessages.content = 'Content is required'
  }

  if (form.alias && !/^[a-z0-9-_]+$/.test(form.alias)) {
    formErrors.alias = true
    formErrorMessages.alias = 'Alias must be lowercase, alphanumeric and hyphen'
  }

  return Object.values(formErrors).every((error) => !error)
}

const resetError = () => {
  formErrors.title = false
  formErrors.alias = false
  formErrors.content = false

  formErrorMessages.title = ''
  formErrorMessages.alias = ''
  formErrorMessages.content = ''
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
        <SavePasteForm
          v-model="form"
          :disabled="loading"
          :form-error-messages="formErrorMessages"
          :form-errors="formErrors"
        />
        <button :disabled="loading" class="btn btn-active btn-ghost w-full" @click="handleCreate">
          <span v-if="loading" class="loading loading-spinner loading-xs"></span>
          Create Paste
        </button>
      </div>
    </div>
  </MainLayout>
</template>

<style scoped></style>
