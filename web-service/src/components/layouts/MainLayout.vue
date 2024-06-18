<script lang="ts" setup>
import UNavbar from '@/components/navbar/UNavbar.vue'
import UFooter from '@/components/footer/UFooter.vue'
import { useUserStore } from '@/stores/user.store'
import { onMounted } from 'vue'

const userStore = useUserStore()

onMounted(() => {
  userStore.loadUserFromLocalStorage()
})
</script>

<template>
  <main class="flex flex-col w-screen h-screen flex-grow overflow-x-hidden scroller-y">
    <UNavbar />
    <section class="flex-1 flex justify-center">
      <div class="container max-w-6xl pt-5 pb-3">
        <slot />
      </div>
    </section>
    <UFooter />
  </main>
</template>
<style scoped></style>
