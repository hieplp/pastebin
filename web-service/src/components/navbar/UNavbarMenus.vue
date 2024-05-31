<script setup lang="ts">
import IconHamburger from '@/components/icons/IconHamburger.vue'
import UNavbarMenusContent from '@/components/navbar/UNavbarMenusContent.vue'
</script>

<template>
  <div class="drawer drawer-end flex-none">
    <!-- Hamburger Button -->
    <input id="u-nav-menus" type="checkbox" class="drawer-toggle" />
    <div class="drawer-content">
      <label for="u-nav-menus" class="drawer-button btn btn-square btn-ghost">
        <IconHamburger />
      </label>
    </div>

    <!-- Drawer Content -->
    <div class="drawer-side">
      <label for="u-nav-menus" aria-label="close sidebar" class="drawer-overlay"></label>
      <UNavbarMenusContent />
    </div>
  </div>
</template>

<style scoped></style>
