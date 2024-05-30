<script setup lang="ts">
import CenterLayout from '@/components/layouts/CenterLayout.vue'
import { reactive } from 'vue'
import BrandIcon from '@/components/BrandIcon.vue'

const form = reactive({
  username: '',
  password: '',
  name: '',
  confirmPassword: ''
})

const handleLogin = () => {
  console.log(form)
}
</script>

<template>
  <CenterLayout>
    <div
      class="flex flex-col items-center justify-center px-6 py-8 mx-auto md:h-screen lg:py-0 w-1/4"
    >
      <BrandIcon />

      <div
        class="w-full bg-white rounded-lg shadow dark:border md:mt-0 sm:max-w-xl xl:p-0 dark:bg-gray-800 dark:border-gray-700"
      >
        <div class="p-6 space-y-4 md:space-y-6 sm:p-8">
          <h1
            class="text-xl font-bold leading-tight tracking-tight text-gray-900 md:text-2xl dark:text-white"
          >
            Create an account
          </h1>
          <form class="space-y-4 md:space-y-6" action="#">
            <label class="input input-bordered flex items-center gap-2">
              Username
              <input
                type="text"
                name="username"
                class="grow"
                placeholder="hieplp"
                v-model="form.username"
              />
            </label>

            <label class="input input-bordered flex items-center gap-2">
              Name
              <input
                type="text"
                name="name"
                class="grow"
                placeholder="••••••••"
                v-model="form.name"
              />
            </label>

            <label class="input input-bordered flex items-center gap-2">
              Password
              <input
                type="password"
                name="password"
                class="grow"
                placeholder="••••••••"
                v-model="form.password"
              />
            </label>

            <label class="input input-bordered flex items-center gap-2">
              Confirm password
              <input
                type="password"
                name="confirmPassword"
                class="grow"
                placeholder="••••••••"
                v-model="form.confirmPassword"
              />
            </label>

            <button class="btn btn-active btn-ghost w-full" @click="handleLogin">Sign up</button>

            <p class="text-sm font-light text-center">
              Have an account?
              <a href="#" class="font-medium hover:underline"> Sign in </a>
            </p>
          </form>
        </div>
      </div>
    </div>
  </CenterLayout>
</template>

<style scoped></style>
