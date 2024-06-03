<script lang="ts" setup>
interface ConfirmationDialogProps {
  open: boolean
  title: string
  subtitle: string
  onClose: () => void
  onConfirm: () => void
}

const { open, title, subtitle, onClose, onConfirm } = defineProps<ConfirmationDialogProps>()
</script>

<template>
  <dialog
    :class="{
      'modal-open': open
    }"
    class="modal"
  >
    <div class="modal-box">
      <h3 class="font-bold text-lg">{{ title }}</h3>
      <p class="py-4">{{ subtitle }}</p>
      <div class="modal-action flex">
        <button class="btn btn-outline" @click="onClose">Cancel</button>
        <button class="btn btn-active btn-ghost" @click="onConfirm">Confirm</button>
      </div>
    </div>
  </dialog>
</template>

<style scoped></style>
