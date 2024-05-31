import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/sign-in',
      name: 'signIn',
      component: () => import('../views/auth/SignInView.vue')
    },
    {
      path: '/sign-up',
      name: 'signUp',
      component: () => import('../views/auth/SignUpView.vue')
    },
    {
      path: '/own-pastes',
      name: 'pastes',
      component: () => import('../views/paste/OwnPastesView.vue')
    }
  ]
})

export default router
