<script lang="ts" setup>
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
    <form action="#" class="space-y-4 md:space-y-6">
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
        <span class="label label-text"> Password </span>
        <input
          v-model="form.password"
          class="input input-bordered w-full"
          name="password"
          placeholder="••••••••"
          type="text"
        />
      </label>

      <div class="flex items-center justify-between">
        <div class="form-control">
          <label class="label cursor-pointer">
            <input checked class="checkbox checkbox-sm" type="checkbox" />
            <span class="label-text ml-2">Remember me</span>
          </label>
        </div>

        <!-- <a href="#" class="text-sm font-medium hover:underline"> Forgot password? </a>-->
      </div>

      <button class="btn btn-active btn-ghost w-full" @click="handleLogin">Sign in</button>

      <p class="text-sm font-light text-center">
        Don’t have an account yet?
        <a class="font-medium hover:underline" href="/sign-up"> Sign up </a>
      </p>
    </form>
  </AuthLayout>
</template>

<style scoped></style>
