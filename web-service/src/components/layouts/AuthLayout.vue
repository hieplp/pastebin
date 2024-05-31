<script lang="ts" setup>
import BrandIcon from '@/components/BrandIcon.vue'
import CenterLayout from '@/components/layouts/CenterLayout.vue'
import UBox from '@/components/box/UBox.vue'
import UTitle from '@/components/typography/UTitle.vue'

export interface AuthLayoutProps {
  title: string
}

const { title } = defineProps<AuthLayoutProps>()
</script>

<template>
  <CenterLayout>
    <BrandIcon />
    <UBox>
      <div class="p-6 space-y-4 md:space-y-6 sm:p-8">
        <UTitle>
          {{ title }}
        </UTitle>
        <slot></slot>
      </div>
    </UBox>
  </CenterLayout>
</template>

<style scoped></style>
