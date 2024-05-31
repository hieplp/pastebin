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
    }
  ]
})

export default router
