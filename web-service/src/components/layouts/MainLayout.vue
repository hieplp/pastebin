<script setup lang="ts">
import UNavbar from '@/components/navbar/UNavbar.vue'
import UFooter from '@/components/footer/UFooter.vue'
</script>

<template>
  <main class="flex flex-col w-screen h-screen flex-grow">
    <UNavbar />
    <section class="flex-1">
      <slot />
    </section>
    <UFooter />
  </main>
</template>

<style scoped></style>
