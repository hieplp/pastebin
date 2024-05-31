<script lang="ts" setup>
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
    <form action="#" class="space-y-6">
      <div class="space-y-1">
        <label class="form-control w-full">
          <span class="label label-text"> Username </span>
          <input
            v-model="form.username"
            class="input input-bordered w-full"
            name="username"
            placeholder="hieplp"
            type="text"
          />
        </label>

        <label class="form-control w-full">
          <span class="label label-text"> Name </span>
          <input
            v-model="form.name"
            class="input input-bordered w-full"
            name="name"
            placeholder="Hiep Ly"
            type="text"
          />
        </label>

        <label class="form-control w-full">
          <span class="label label-text"> Password </span>
          <input
            v-model="form.password"
            class="input input-bordered w-full"
            name="password"
            placeholder="••••••••"
            type="text"
          />
        </label>

        <label class="form-control w-full">
          <span class="label label-text"> Confirm Password </span>
          <input
            v-model="form.confirmPassword"
            class="input input-bordered w-full"
            name="confirmPassword"
            placeholder="••••••••"
            type="text"
          />
        </label>
      </div>

      <button class="btn mt-2 btn-active btn-ghost w-full" @click="handleLogin">Sign up</button>

      <p class="text-sm font-light text-center">
        Have an account?
        <a class="font-medium hover:underline" href="/sign-in"> Sign in </a>
      </p>
    </form>
  </AuthLayout>
</template>

<style scoped></style>
