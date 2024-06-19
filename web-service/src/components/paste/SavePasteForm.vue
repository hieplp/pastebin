<script lang="ts" setup>
import ExpiryConstants from '@/constants/ExpiryConstants'
import PrivacyConstants from '@/constants/PrivacyConstants'
import UCodeMirror from '@/components/codemirror/UCodeMirror.vue'
import { computed } from 'vue'

interface SavePasteFormType {
  modelValue: {
    alias: string
    title: string
    content: string
    expiry: string
    privacy: string
  }
  formErrors: {
    alias: boolean
    title: boolean
    content: boolean
  }
  formErrorMessages: {
    alias: string
    title: string
    content: string
  }
  disabled?: boolean
}

const { modelValue, formErrors, formErrorMessages, disabled } = defineProps<SavePasteFormType>()

const emit = defineEmits<{
  (event: 'update:modelValue', ...args: any[]): void
}>()

const form = computed({
  get: () => modelValue,
  set: (value) => emit('update:modelValue', value)
})
</script>

<template>
  <div class="space-y-3">
    <label class="form-control w-full">
      <span class="label label-text"> Title </span>
      <input
        v-model="form.title"
        :disabled="disabled"
        class="input input-bordered w-full"
        name="title"
        placeholder="Alice in Wonderland"
        type="text"
      />
      <label v-if="formErrors.title" class="label label-text-alt text-error">
        {{ formErrorMessages.title }}
      </label>
    </label>

    <label class="form-control w-full">
      <span class="label label-text"> Alias </span>
      <input
        v-model="form.alias"
        :disabled="disabled"
        class="input input-bordered w-full"
        name="alias"
        placeholder="alice-in-wonderland"
        type="text"
      />
      <label v-if="formErrors.alias" class="label label-text-alt text-error">
        {{ formErrorMessages.alias }}
      </label>
    </label>

    <UCodeMirror
      v-model="form.content"
      :disabled="disabled"
      :error="formErrors.content"
      :errorMessage="formErrorMessages.content"
    />

    <div class="grid grid-cols-2 space-x-2">
      <label class="form-control w-full flex-1">
        <span class="label label-text"> Expiry </span>
        <select v-model="form.expiry" :disabled="disabled" class="select">
          <option v-for="option in ExpiryConstants.Options" :key="option.id" :value="option.value">
            {{ option.label }}
          </option>
        </select>
      </label>

      <label class="form-control w-full flex-1">
        <span class="label label-text"> Privacy </span>
        <select v-model="form.privacy" :disabled="disabled" class="select">
          <option v-for="option in PrivacyConstants.Options" :key="option.id" :value="option.value">
            {{ option.label }}
          </option>
        </select>
      </label>
    </div>
  </div>
</template>

<style scoped></style>
