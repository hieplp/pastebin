<script setup lang="ts">
type MenuType = {
  id: string
  name: string
  url?: string
  children?: MenuItemType[]
}

type MenuItemType = {
  id: string
  name: string
  url: string
}

const menus: MenuType[] = [
  {
    id: 'home',
    name: 'Home',
    url: '/'
  },
  {
    id: 'about',
    name: 'About',
    url: '/about'
  },
  {
    id: 'pastebin',
    name: 'Pastebin',
    url: '/',
    children: [
      {
        id: 'owned',
        name: 'Owned',
        url: '/own-pastes'
      },
      {
        id: 'public',
        name: 'Public',
        url: '/public-pastes'
      },
      {
        id: 'create',
        name: 'Create',
        url: '/create-paste'
      }
    ]
  }
]
</script>

<template>
  <ul class="menu p-4 w-96 min-h-full bg-base-200 text-base-content">
    <!-- Menus -->
    <li v-for="menu in menus" :key="menu.id">
      <!-- Single Menu -->
      <template v-if="!menu.children || menu.children.length === 0">
        <a :href="menu.url" class="font-bold">
          {{ menu.name }}
        </a>
      </template>

      <!-- Menu With Children -->
      <template v-else>
        <details open>
          <!-- Menu Title -->
          <summary>
            <h2 class="font-bold">
              {{ menu.name }}
            </h2>
          </summary>

          <!-- Menu Children -->
          <ul>
            <li v-for="item in menu.children" :key="item.id">
              <a :href="item.url">{{ item.name }}</a>
            </li>
          </ul>
        </details>
      </template>
    </li>

    <!-- User Profile -->
    <li class="mt-auto">
      <div class="flex flex-row">
        <div class="avatar">
          <div class="w-10 rounded-full">
            <img
              src="https://img.daisyui.com/images/stock/photo-1534528741775-53994a69daeb.jpg"
              alt="avatar"
            />
          </div>
        </div>

        <p class="text-lg">Hi, <strong>John Doe</strong></p>

        <!-- Logout -->
        <button class="btn btn-ghost ml-auto text-red-500">Sign out</button>
      </div>
    </li>
  </ul>
</template>

<style scoped></style>
