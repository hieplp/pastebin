<!-- See more: https://github.com/surmon-china/surmon-china.github.io/blob/source/examples/vue-codemirror/editor.vue-->
<script lang="ts" setup>
import { computed, reactive, shallowRef } from 'vue'
import { Codemirror } from 'vue-codemirror'
import UCodeMirrorOptions from '@/components/codemirror/UCodeMirrorOptions.vue'
import CodeMirrorOptions from '@/components/codemirror/CodeMirrorOptions'
import type OptionType from '@/types/option.type'

interface UCodeMirrorProps {
  modelValue: string
}

const { modelValue } = defineProps<UCodeMirrorProps>()

const emit = defineEmits<{
  (event: 'update:modelValue', ...args: any[]): void
}>()

const inputModel = computed({
  get: () => modelValue,
  set: (value) => emit('update:modelValue', value)
})

const view = shallowRef()

const config = reactive({
  language: CodeMirrorOptions.Languages[0],
  tabSize: CodeMirrorOptions.TabSizes[0],
  theme: CodeMirrorOptions.Themes[0]
})

const extensions = computed(() => {
  return [config.language.value, config.theme.value]
})

function onOptionChange(type: string, payload: OptionType) {
  switch (type) {
    case CodeMirrorOptions.Type.Language:
      config.language = payload
      break
    case CodeMirrorOptions.Type.TabSize:
      config.tabSize = payload
      break
    case CodeMirrorOptions.Type.Theme:
      config.theme = payload
      break
    default:
      break
  }
}

function handleReady(payload: any) {
  view.value = payload.view
}
</script>

<template>
  <div class="">
    <UCodeMirrorOptions :onOptionsChange="onOptionChange" />

    <Codemirror
      v-model="inputModel"
      :autofocus="true"
      :extensions="extensions"
      :indent-with-tab="true"
      :style="{ height: '400px' }"
      :tab-size="config.tabSize.value"
      class="rounded mt-5"
      placeholder="Code goes here..."
      @ready="handleReady"
    />
  </div>
</template>

<style scoped></style>
