import { create } from 'zustand'
import { persist } from 'zustand/middleware'

export type Theme = 'light' | 'dark' | 'system'

export interface ThemeState {
  theme: Theme
  resolvedTheme: 'light' | 'dark'
}

export interface ThemeActions {
  setTheme: (theme: Theme) => void
  toggleTheme: () => void
}

export type ThemeStore = ThemeState & ThemeActions

function getSystemTheme(): 'light' | 'dark' {
  if (typeof window === 'undefined') return 'dark'
  return window.matchMedia('(prefers-color-scheme: dark)').matches ? 'dark' : 'light'
}

function applyTheme(theme: Theme): 'light' | 'dark' {
  const resolved = theme === 'system' ? getSystemTheme() : theme
  if (typeof window !== 'undefined') {
    const root = document.documentElement
    if (resolved === 'dark') {
      root.classList.add('dark')
    } else {
      root.classList.remove('dark')
    }
    root.style.colorScheme = resolved
  }
  return resolved
}

export const INITIAL_STATE: ThemeState = {
  theme: 'system',
  resolvedTheme: applyTheme('system'),
}

export const INITIAL_THEME_STATE = INITIAL_STATE

// ponytail: shared theme store backed by Zustand persist middleware and media listener
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
        ...INITIAL_STATE,

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
    }
  )
)

export const useTheme = useThemeStore
