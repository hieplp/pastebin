import { defineStore } from 'pinia'
import type PasteType from '@/types/paste.type'

export const usePasteStore = defineStore({
  id: 'paste',
  state: () => ({
    paste: {} as PasteType,
    pastes: [] as PasteType[]

  }),
  actions: {
    setPastes(pastes: PasteType[]) {
      this.pastes = pastes
    },

    setPaste(paste: PasteType) {
      this.paste = paste
    }
  }
})