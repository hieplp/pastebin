import type { ResolvedTheme, Theme } from '@/types'
import { applyTheme } from '@/utils'
import { create } from 'zustand'
import { persist } from 'zustand/middleware'

/* ---- States ---- */

interface ThemeState {
  theme: Theme
  resolvedTheme: ResolvedTheme
}

const INITIAL_STATE: ThemeState = {
  theme: 'system',
  resolvedTheme: applyTheme('system'),
}

/* ---- Actions ---- */

interface ThemeActions {
  setTheme: (theme: Theme) => void
  toggleTheme: () => void
}

/* ---- Stores ---- */

export type ThemeStore = ThemeState & ThemeActions

// theme store backed by persist middleware, reacting to system color-scheme changes
export const useThemeStore = create<ThemeStore>()(
  persist(
    (set, get) => {
      if (typeof window !== 'undefined') {
        const media = window.matchMedia('(prefers-color-scheme: dark)')
        media.addEventListener('change', () => {
          if (get().theme === 'system') {
            set({ resolvedTheme: applyTheme('system') })
          }
        })
      }

      return {
        // State
        ...INITIAL_STATE,

        // Actions
        setTheme: (theme: Theme) => {
          const resolvedTheme = applyTheme(theme)
          set({ theme, resolvedTheme })
        },

        toggleTheme: () => {
          const next: Theme = get().resolvedTheme === 'dark' ? 'light' : 'dark'
          const resolvedTheme = applyTheme(next)
          set({ theme: next, resolvedTheme })
        },
      }
    },
    {
      name: 'theme',
      onRehydrateStorage: () => (state) => {
        if (state) {
          state.resolvedTheme = applyTheme(state.theme)
        }
      },
    },
  ),
)

export const useTheme = useThemeStore
