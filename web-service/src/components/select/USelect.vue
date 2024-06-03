<script lang="ts" setup>
import type OptionType from '@/types/option.type'
import { ref } from 'vue'

interface USelectProps {
  label: string
  options: OptionType[]
  onChange: (payload: OptionType) => void
}

const { label, options, onChange } = defineProps<USelectProps>()

const selected = ref(options[0].id)

function handleChange(e: Event) {
  const target = e.target as HTMLSelectElement
  const option = options.find((option) => option.id === target.value)
  if (option) {
    onChange(option)
  }
}
</script>

<template>
  <select v-model="selected" class="select select-sm" @change="handleChange">
    <option disabled selected value="">{{ label }}</option>
    <option v-for="option in options" :key="option.id" :value="option.id">
      {{ option.label }}
    </option>
  </select>
</template>

<style scoped></style>
