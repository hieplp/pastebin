<script lang="ts" setup>
import IconTwitter from '@/components/icons/IconTwitter.vue'
import IconYoutube from '@/components/icons/IconYoutube.vue'
import IconFacebook from '@/components/icons/IconFacebook.vue'
import type { Component } from 'vue'

type SocialType = {
  name: string
  icon: Component
  url: string
}

const socials: SocialType[] = [
  { name: 'Twitter', icon: IconTwitter, url: '#' },
  { name: 'Youtube', icon: IconYoutube, url: '#' },
  { name: 'Facebook', icon: IconFacebook, url: '#' }
]
</script>

<template>
  <footer class="dark:bg-gray-800 dark:border-gray-100 justify-center flex py-5">
    <div
      class="footer md:max-w-6xl flex flex-col md:flex-row justify-center md:justify-between items-center"
    >
      <aside class="items-center grid-flow-col">
        <p>Copyright © {{ new Date().getFullYear() }} - All right reserved</p>
      </aside>
      <nav class="grid-flow-col gap-4 md:place-self-center md:justify-self-end">
        <a
          v-for="social in socials"
          :key="social.name"
          :href="social.url"
          class="hover:text-gray-600"
        >
          <component :is="social.icon" />
        </a>
      </nav>
    </div>
  </footer>
</template>
