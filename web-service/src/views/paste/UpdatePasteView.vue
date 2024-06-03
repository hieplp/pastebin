<script lang="ts" setup>
import MainLayout from '@/components/layouts/MainLayout.vue'
import { reactive, ref } from 'vue'
import PrivacyConstants from '@/constants/PrivacyConstants'
import ExpiryConstants from '@/constants/ExpiryConstants'
import SavePasteForm from '@/components/paste/SavePasteForm.vue'
import ConfirmationDialog from '@/components/dialog/ConfirmationDialog.vue'

const form = reactive({
  title: '',
  content: '',
  expiry: ExpiryConstants.Options[0].id,
  privacy: PrivacyConstants.Options[0].id
})

const updateDialogVisible = ref<boolean>(false)
const deleteDialogVisible = ref<boolean>(false)

function handleCancel() {
  console.log('cancel')
}

function handleUpdate() {
  updateDialogVisible.value = true
}

function handleDelete() {
  deleteDialogVisible.value = true
}

function triggerUpdateDialog() {
  updateDialogVisible.value = !updateDialogVisible.value
}

function triggerDeleteDialog() {
  deleteDialogVisible.value = !deleteDialogVisible.value
}
</script>

<template>
  <MainLayout>
    <div class="px-6 pb-6">
      <div class="text-sm breadcrumbs flex">
        <ul>
          <li><a href="/">Home</a></li>
          <li><a href="/own-pastes">Pastes</a></li>
          <li>Update Paste</li>
        </ul>

        <button class="btn btn-sm btn-ghost ml-auto" @click="handleDelete">Delete</button>
      </div>

      <div class="space-y-4 md:space-y-6">
        <SavePasteForm v-model="form" />

        <div class="grid grid-cols-2 gap-x-2">
          <button class="btn btn-outline w-full" @click="handleCancel">Cancel</button>
          <button class="btn btn-active btn-ghost" @click="triggerUpdateDialog">Update</button>
        </div>
      </div>

      <ConfirmationDialog
        :onClose="triggerUpdateDialog"
        :onConfirm="handleUpdate"
        :open="updateDialogVisible"
        subtitle="Do you want to update this paste?"
        title="Update Paste"
      />

      <ConfirmationDialog
        :onClose="triggerDeleteDialog"
        :onConfirm="handleDelete"
        :open="deleteDialogVisible"
        subtitle="Do you want to delete this paste?"
        title="Delete Paste"
      />
    </div>
  </MainLayout>
</template>

<style scoped></style>
