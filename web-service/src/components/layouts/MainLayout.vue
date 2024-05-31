<script lang="ts" setup>
import UNavbar from '@/components/navbar/UNavbar.vue'
import UFooter from '@/components/footer/UFooter.vue'
</script>

<template>
  <main class="flex flex-col w-screen h-screen flex-grow">
    <UNavbar />
    <section class="flex-1 flex justify-center">
      <div class="container max-w-6xl py-2">
        <slot />
      </div>
    </section>
    <UFooter />
  </main>
</template>

<style scoped></style>
