<script lang="ts" setup>
import { reactive } from 'vue'
import AuthLayout from '@/components/layouts/AuthLayout.vue'
import AuthService from '@/services/auth.service'

const form = reactive({
  username: '',
  password: '',
  name: '',
  confirmPassword: ''
})

const formErrorsMessage = reactive({
  username: '',
  password: '',
  name: '',
  confirmPassword: ''
})

const formErrors = reactive({
  username: false,
  password: false,
  name: false,
  confirmPassword: false
})

const handleRegister = () => {
  if (!validateForm()) {
    console.log('Form is invalid')
    return
  }

  AuthService.register({
    username: form.username,
    name: form.name,
    // TODO: Hash password before sending to the server. Maybe encrypt too
    password: form.password
  })
    .then(() => {
      console.log('Register successfully')
    })
    .catch((error) => {
      console.error(error)
    })
}

const resetError = () => {
  formErrors.username = false
  formErrors.password = false
  formErrors.password = false
  formErrors.confirmPassword = false

  formErrorsMessage.username = ''
  formErrorsMessage.password = ''
  formErrorsMessage.name = ''
  formErrorsMessage.confirmPassword = ''
}

const validateForm = () => {
  resetError()

  if (form.username === '') {
    formErrors.username = true
    formErrorsMessage.username = 'Username is required'
  }

  if (form.name === '') {
    formErrors.name = true
    formErrorsMessage.name = 'Name is required'
  }

  if (form.password === '') {
    formErrors.password = true
    formErrorsMessage.password = 'Password is required'
  }

  if (form.confirmPassword === '') {
    formErrors.confirmPassword = true
    formErrorsMessage.confirmPassword = 'Confirm password is required'
  } else if (form.confirmPassword !== form.password) {
    formErrors.confirmPassword = true
    formErrorsMessage.confirmPassword = 'Password and confirm password do not match'
  }

  return !Object.values(formErrors).some((error) => error)
}
</script>

<template>
  <AuthLayout title="Create an account">
    <div class="space-y-6">
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
          <label v-if="formErrors.username" class="label label-text-alt text-error">
            {{ formErrorsMessage.username }}
          </label>
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
          <label v-if="formErrors.name" class="label label-text-alt text-error">
            {{ formErrorsMessage.name }}
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

        <label class="form-control w-full">
          <span class="label label-text"> Confirm Password </span>
          <input
            v-model="form.confirmPassword"
            class="input input-bordered w-full"
            name="confirmPassword"
            placeholder="••••••••"
            type="password"
          />
          <label v-if="formErrors.confirmPassword" class="label label-text-alt text-error">
            {{ formErrorsMessage.confirmPassword }}
          </label>
        </label>
      </div>

      <button class="btn mt-2 btn-active btn-ghost w-full" @click="handleRegister">Sign up</button>

      <p class="text-sm font-light text-center">
        Have an account?
        <a class="font-medium hover:underline" href="/sign-in"> Sign in </a>
      </p>
    </div>
  </AuthLayout>
</template>

<style scoped></style>
