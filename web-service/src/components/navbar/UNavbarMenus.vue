<script lang="ts" setup>
import IconHamburger from '@/components/icons/IconHamburger.vue'
import UNavbarMenusContent from '@/components/navbar/UNavbarMenusContent.vue'
import { useUserStore } from '@/stores/user.store'

const userStore = useUserStore()
const user = userStore.user
</script>

<template>
  <div class="drawer drawer-end flex-none">
    <!-- Hamburger Button -->
    <input id="u-nav-menus" class="drawer-toggle" type="checkbox" />
    <div class="drawer-content">
      <label class="drawer-button btn btn-square btn-ghost" for="u-nav-menus">
        <IconHamburger />
      </label>
    </div>

    <!-- Drawer Content -->
    <div class="drawer-side">
      <label aria-label="close sidebar" class="drawer-overlay" for="u-nav-menus"></label>
      <UNavbarMenusContent :user="user" />
    </div>
  </div>
</template>

<style scoped></style>
