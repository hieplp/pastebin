import type { AlertType } from '@/types'
import { create } from 'zustand'

/* ---- States ---- */

interface AlertState {
  message: string | null
  type: AlertType
}

const INITIAL_STATE: AlertState = {
  message: null,
  type: 'success',
}

// global alert stores with auto-dismiss for success, shared across pages
let alertTimer: number | undefined

/* ---- Actions ---- */

interface AlertActions {
  showAlert: (message: string, type?: AlertType) => void
  showSuccessAlert: (message: string) => void
  showErrorAlert: (message: string) => void
  dismissAlert: () => void
}

/* ---- Stores ---- */

export type AlertStore = AlertState & AlertActions

export const useAlertStore = create<AlertStore>()((set, _) => ({
  // State
  ...INITIAL_STATE,

  // Actions
  showAlert: (message: string, type: AlertType = 'success') => {
    clearTimeout(alertTimer)
    set({ message, type })
    if (type === 'success') {
      // @ts-ignore
      alertTimer = setTimeout(() => set({ message: null }), 5000)
    }
  },

  dismissAlert: () => {
    clearTimeout(alertTimer)
    set({ message: null })
  },

  showSuccessAlert: (message: string) => {
    clearTimeout(alertTimer)
    set({ message, type: 'success' })
    // @ts-ignore
    alertTimer = setTimeout(() => set({ message: null }), 5000)
  },

  showErrorAlert: (message: string) => {
    clearTimeout(alertTimer)
    set({ message, type: 'error' })
  },
}))
