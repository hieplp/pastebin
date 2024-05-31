<script setup lang="ts">
import { reactive } from 'vue'
import AuthLayout from '@/components/layouts/AuthLayout.vue'

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
  <AuthLayout title="Create an account">
    <form class="space-y-6" action="#">
      <div class="space-y-1">
        <label class="form-control w-full">
          <span class="label label-text"> Username </span>
          <input
            type="text"
            name="username"
            class="input input-bordered w-full"
            placeholder="hieplp"
            v-model="form.username"
          />
        </label>

        <label class="form-control w-full">
          <span class="label label-text"> Name </span>
          <input
            type="text"
            name="name"
            class="input input-bordered w-full"
            placeholder="Hiep Ly"
            v-model="form.name"
          />
        </label>

        <label class="form-control w-full">
          <span class="label label-text"> Password </span>
          <input
            type="text"
            name="password"
            class="input input-bordered w-full"
            placeholder="••••••••"
            v-model="form.password"
          />
        </label>

        <label class="form-control w-full">
          <span class="label label-text"> Confirm Password </span>
          <input
            type="text"
            name="confirmPassword"
            class="input input-bordered w-full"
            placeholder="••••••••"
            v-model="form.confirmPassword"
          />
        </label>
      </div>

      <button class="btn mt-2 btn-active btn-ghost w-full" @click="handleLogin">Sign up</button>

      <p class="text-sm font-light text-center">
        Have an account?
        <a href="/sign-in" class="font-medium hover:underline"> Sign in </a>
      </p>
    </form>
  </AuthLayout>
</template>

<style scoped></style>
