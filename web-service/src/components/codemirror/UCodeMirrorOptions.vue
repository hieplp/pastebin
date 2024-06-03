<script lang="ts" setup>
import USelect from '@/components/select/USelect.vue'
import CodeMirrorOptions from '@/components/codemirror/CodeMirrorOptions'
import type OptionType from '@/types/option.type'

export interface UCodeMirrorOptionsProps {
  onOptionsChange: (type: string, payload: OptionType) => void
}

const { onOptionsChange } = defineProps<UCodeMirrorOptionsProps>()
</script>

<template>
  <div class="rounded-t h-30 p-3 w-full bg-gray-700 flex justify-end space-x-2">
    <USelect
      :onChange="(payload: OptionType) => onOptionsChange(CodeMirrorOptions.Type.Language, payload)"
      :options="CodeMirrorOptions.Languages"
      label="Language"
    />
    <USelect
      :onChange="(payload: OptionType) => onOptionsChange(CodeMirrorOptions.Type.TabSize, payload)"
      :options="CodeMirrorOptions.TabSizes"
      label="Tab Size"
    />
    <USelect
      :onChange="(payload: OptionType) => onOptionsChange(CodeMirrorOptions.Type.Theme, payload)"
      :options="CodeMirrorOptions.Themes"
      label="Theme"
    />
  </div>
</template>

<style scoped></style>
