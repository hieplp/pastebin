import { defineStore } from 'pinia'
import type UserType from '@/types/user.type'
import StorageConstants from '@/constants/StorageConstants'
import { LocalStorage } from '@/utils/storage.util'

export const useUserStore = defineStore({
  id: 'user',
  state: () => ({
    user: {} as UserType,
    token: null
  }),
  getters: {
    isAuthenticated: (state) => !!state.user,
    getUser: (state) => state.user
  },
  actions: {
    updateUser(user: UserType) {
      this.user = user
    },

    loadUserFromLocalStorage() {
      if (this.user && Object.keys(this.user).length > 0) {
        return
      }

      this.user = LocalStorage.get(StorageConstants.USER) as UserType
    }
  }
})
