<script lang="ts" setup>
import { reactive } from 'vue'
import AuthLayout from '@/components/layouts/AuthLayout.vue'
import { useRoute, useRouter } from 'vue-router'
import { useToast } from 'vue-toastification'
import AuthService from '@/services/auth.service'
import Md5Util from '@/utils/md5.util'
import ErrorCode from '@/constants/ErrorCode'
import CookieUtil from '@/utils/cookie.util'
import CookieConstants from '@/constants/CookieConstants'
import StorageConstants from '@/constants/StorageConstants'
import { LocalStorage } from '@/utils/storage.util'

// ----------------------------------------
const form = reactive({
  username: '',
  password: '',
  rememberMe: false
})

const formErrors = reactive({
  username: false,
  password: false
})

const formErrorsMessage = reactive({
  username: '',
  password: ''
})

// ----------------------------------------

const route = useRoute()

const router = useRouter()

const toast = useToast()

// ----------------------------------------
const handleLogin = () => {
  if (!validateForm()) {
    return
  }

  AuthService.login({
    username: form.username,
    password: Md5Util.hash(form.password)
  })
    .then((data) => {
      const { refreshToken, user } = data

      CookieUtil.save(CookieConstants.USER_ID, user.userId, refreshToken.expiredAt)

      LocalStorage.set(StorageConstants.USER, user)

      toast.success('Login successfully')

      if (route.query.redirect) {
        router.push(route.query.redirect as string)
      } else {
        router.push('/own-pastes')
      }
    })
    .catch((error) => {
      console.log(error)
      switch (error) {
        case ErrorCode.UNAUTHORIZED:
          formErrors.password = true
          formErrorsMessage.password = 'Invalid username or password'
          break
      }
    })
}

const validateForm = () => {
  resetError()

  if (!form.username) {
    formErrors.username = true
    formErrorsMessage.username = 'Username is required'
  }

  if (!form.password) {
    formErrors.password = true
    formErrorsMessage.password = 'Password is required'
  }

  return Object.values(formErrors).every((error) => !error)
}

const resetError = () => {
  formErrors.username = false
  formErrors.password = false

  formErrorsMessage.username = ''
  formErrorsMessage.password = ''
}
</script>

<template>
  <AuthLayout title="Sign in">
    <div class="space-y-4 md:space-y-6">
      <label class="form-control w-full">
        <span class="label label-text"> Username </span>
        <input
          v-model="form.username"
          class="input input-bordered w-full"
          name="username"
          placeholder="hieplp"
          type="text"
        />
        <label v-if="formErrors.username" class="label label-text-alt text-error">
          {{ formErrorsMessage.username }}
        </label>
      </label>

      <label class="form-control w-full">
        <span class="label label-text"> Password </span>
        <input
          v-model="form.password"
          class="input input-bordered w-full"
          name="password"
          placeholder="••••••••"
          type="password"
        />
        <label v-if="formErrors.password" class="label label-text-alt text-error">
          {{ formErrorsMessage.password }}
        </label>
      </label>

      <div class="flex items-center justify-between">
        <div class="form-control">
          <label class="label cursor-pointer">
            <input v-model="form.rememberMe" checked class="checkbox checkbox-sm" type="checkbox" />
            <span class="label-text ml-2"> Remember me </span>
          </label>
        </div>

        <!-- <a href="#" class="text-sm font-medium hover:underline"> Forgot password? </a>-->
      </div>

      <button class="btn btn-active btn-ghost w-full" @click="handleLogin">Sign in</button>

      <p class="text-sm font-light text-center">
        Don’t have an account yet?
        <a class="font-medium hover:underline" href="/sign-up"> Sign up </a>
      </p>
    </div>
  </AuthLayout>
</template>

<style scoped></style>
