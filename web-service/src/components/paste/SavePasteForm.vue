<script lang="ts" setup>
import ExpiryConstants from '@/constants/ExpiryConstants'
import PrivacyConstants from '@/constants/PrivacyConstants'
import UCodeMirror from '@/components/codemirror/UCodeMirror.vue'
import { computed } from 'vue'

interface SavePasteFormType {
  modelValue: {
    title: string
    content: string
    expiry: string
    privacy: string
  }
}

const { modelValue } = defineProps<SavePasteFormType>()

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
        class="input input-bordered w-full"
        name="title"
        placeholder="Alice in Wonderland"
        type="text"
      />
    </label>

    <UCodeMirror v-model="form.content" />

    <div class="grid grid-cols-2 space-x-2">
      <label class="form-control w-full flex-1">
        <span class="label label-text"> Expiry </span>
        <select v-model="form.expiry" class="select">
          <option v-for="option in ExpiryConstants.Options" :key="option.id" :value="option.id">
            {{ option.label }}
          </option>
        </select>
      </label>

      <label class="form-control w-full flex-1">
        <span class="label label-text"> Privacy </span>
        <select v-model="form.privacy" class="select">
          <option v-for="option in PrivacyConstants.Options" :key="option.id" :value="option.id">
            {{ option.label }}
          </option>
        </select>
      </label>
    </div>
  </div>
</template>

<style scoped></style>
