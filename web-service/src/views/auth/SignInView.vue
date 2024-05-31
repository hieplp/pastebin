<script setup lang="ts">
import { reactive } from 'vue'
import AuthLayout from '@/components/layouts/AuthLayout.vue'

const form = reactive({
  username: '',
  password: ''
})

const handleLogin = () => {
  console.log(form)
}
</script>

<template>
  <AuthLayout title="Sign in">
    <form class="space-y-4 md:space-y-6" action="#">
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
        <span class="label label-text"> Password </span>
        <input
          type="text"
          name="password"
          class="input input-bordered w-full"
          placeholder="••••••••"
          v-model="form.password"
        />
      </label>

      <div class="flex items-center justify-between">
        <div class="form-control">
          <label class="label cursor-pointer">
            <input type="checkbox" checked class="checkbox checkbox-sm" />
            <span class="label-text ml-2">Remember me</span>
          </label>
        </div>

        <!-- <a href="#" class="text-sm font-medium hover:underline"> Forgot password? </a>-->
      </div>

      <button class="btn btn-active btn-ghost w-full" @click="handleLogin">Sign in</button>

      <p class="text-sm font-light text-center">
        Don’t have an account yet?
        <a href="/sign-up" class="font-medium hover:underline"> Sign up </a>
      </p>
    </form>
  </AuthLayout>
</template>

<style scoped></style>
