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
    },
    {
      path: '/create-paste',
      name: 'createPaste',
      component: () => import('../views/paste/OwnPastesView.vue')
      // component: () => import('../views/paste/CreatePasteView.vue')
    },
    {
      path: '/pastes/:id',
      name: 'detailPaste',
      component: () => import('../views/paste/DetailPasteView.vue')
    },
    {
      path: '/create-paste',
      name: 'createPaste',
      component: () => import('../views/paste/CreatePasteView.vue')
    }
    // {
    //   path: '/:pathMatch(.*)*',
    //   name: 'notFound',
    //   component: () => import('../views/NotFoundView.vue')
    // }
  ]
})

export default router
