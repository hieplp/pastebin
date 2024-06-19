import { createRouter, createWebHistory } from 'vue-router'
import CookieUtil from '@/utils/cookie.util'
import CookieConstants from '@/constants/CookieConstants'

const isAuthenticated = (to: any, from: any, next: any) => {
  if (CookieUtil.exists(CookieConstants.USER_ID)) {
    next()
    return
  }

  next({ name: 'signIn', query: { redirect: to.fullPath } })
}

const isNotAuthenticated = (to: any, from: any, next: any) => {
  if (!CookieUtil.exists(CookieConstants.USER_ID)) {
    next()
    return
  }

  next({ name: 'ownPastes' })
}

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/sign-in',
      name: 'signIn',
      component: () => import('../views/auth/SignInView.vue'),
      beforeEnter: isNotAuthenticated
    },
    {
      path: '/sign-up',
      name: 'signUp',
      component: () => import('../views/auth/SignUpView.vue'),
      beforeEnter: isNotAuthenticated
    },
    {
      path: '/own-pastes',
      name: 'ownPastes',
      component: () => import('../views/paste/OwnPastesView.vue'),
      beforeEnter: isAuthenticated
    },
    // {
    //   path: '/create-paste',
    //   name: 'createPaste',
    //   component: () => import('../views/paste/OwnPastesView.vue')
    //   // component: () => import('../views/paste/CreatePasteView.vue')
    // },
    {
      path: '/pastes/:id',
      name: 'detailPaste',
      component: () => import('../views/paste/DetailPasteView.vue')
    },
    {
      path: '/edit-paste/:id',
      name: 'editPaste',
      component: () => import('../views/paste/UpdatePasteView.vue')
    },
    {
      path: '/create-paste',
      name: 'createPaste',
      component: () => import('../views/paste/CreatePasteView.vue'),
      beforeEnter: isAuthenticated
    }
  ]
})

export default router
