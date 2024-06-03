<script lang="ts" setup>
import MainLayout from '@/components/layouts/MainLayout.vue'
import { reactive } from 'vue'
import UCodeMirror from '@/components/codemirror/UCodeMirror.vue'
import PrivacyConstants from '@/constants/PrivacyConstants'
import ExpiryConstants from '@/constants/ExpiryConstants'

const form = reactive({
  title: '',
  content: '',
  expiry: ExpiryConstants.Options[0].id,
  privacy: PrivacyConstants.Options[0].id
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
                <option
                  v-for="option in ExpiryConstants.Options"
                  :key="option.id"
                  :value="option.id"
                >
                  {{ option.label }}
                </option>
              </select>
            </label>

            <label class="form-control w-full flex-1">
              <span class="label label-text"> Privacy </span>
              <select v-model="form.privacy" class="select">
                <option
                  v-for="option in PrivacyConstants.Options"
                  :key="option.id"
                  :value="option.id"
                >
                  {{ option.label }}
                </option>
              </select>
            </label>
          </div>
        </div>
        <button class="btn btn-active btn-ghost w-full" @click="handleCreate">Create</button>
      </div>
    </div>
  </MainLayout>
</template>

<style scoped></style>
