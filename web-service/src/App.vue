<script lang="ts" setup>
import { RouterView } from 'vue-router'
</script>

<template>
  <div class="relative bg-gray-50 dark:bg-gray-900 w-screen h-screen">
    <RouterView />

    <!--    <div class="absolute top-0 left-0 w-full h-full flex items-center justify-center bg-gray-50 dark:bg-gray-900 bg-opacity-80 dark:bg-opacity-80">-->
    <!--      <span class="loading loading-spinner loading-lg"></span>-->
    <!--    </div>-->
  </div>
</template>

<style scoped></style>
