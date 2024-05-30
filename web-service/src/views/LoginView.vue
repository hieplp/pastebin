<script setup lang="ts">
import CenterLayout from '@/components/layouts/CenterLayout.vue'
</script>

<template>
  <CenterLayout>
    <div
      class="flex flex-col items-center justify-center px-6 py-8 mx-auto md:h-screen lg:py-0 w-1/4"
    >
      <a
        href="#"
        class="flex items-center mb-6 text-2xl font-semibold text-gray-900 dark:text-white"
      >
        Pastebin
      </a>

      <div
        class="w-full bg-white rounded-lg shadow dark:border md:mt-0 sm:max-w-xl xl:p-0 dark:bg-gray-800 dark:border-gray-700"
      >
        <div class="p-6 space-y-4 md:space-y-6 sm:p-8">
          <h1
            class="text-xl font-bold leading-tight tracking-tight text-gray-900 md:text-2xl dark:text-white"
          >
            Sign in to your account
          </h1>
          <form class="space-y-4 md:space-y-6" action="#">
            <label class="input input-bordered flex items-center gap-2">
              Username
              <input type="text" class="grow" placeholder="hieplp" />
            </label>

            <label class="input input-bordered flex items-center gap-2">
              Password
              <input type="password" class="grow" placeholder="••••••••" />
            </label>

            <div class="flex items-center justify-between">
              <div class="form-control">
                <label class="label cursor-pointer">
                  <input type="checkbox" checked class="checkbox checkbox-sm" />
                  <span class="label-text ml-2">Remember me</span>
                </label>
              </div>

              <a href="#" class="text-sm font-medium hover:underline"> Forgot password? </a>
            </div>

            <button class="btn btn-active btn-ghost w-full">Sign in</button>

            <p class="text-sm font-light text-center">
              Don’t have an account yet?
              <a href="#" class="font-medium hover:underline"> Sign up </a>
            </p>
          </form>
        </div>
      </div>
    </div>
  </CenterLayout>
</template>

<style scoped></style>
