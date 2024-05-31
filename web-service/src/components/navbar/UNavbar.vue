<script setup lang="ts">
import UNavbarTitle from '@/components/navbar/UNavbarTitle.vue'
import UNavbarMenu from '@/components/navbar/UNavbarMenus.vue'
</script>

<template>
  <header class="shadow dark:border dark:bg-gray-800 dark:border-gray-700 justify-center flex">
    <div class="navbar max-w-6xl">
      <UNavbarTitle />

      <div class="flex-none">
        <UNavbarMenu />
      </div>
    </div>
  </header>
</template>

<style scoped></style>
