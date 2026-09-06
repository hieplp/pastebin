import type { ResolvedTheme, Theme } from '@/types'

export function getSystemTheme(): ResolvedTheme {
  if (typeof window === 'undefined') return 'dark'
  return window.matchMedia('(prefers-color-scheme: dark)').matches
    ? 'dark'
    : 'light'
}

export function applyTheme(theme: Theme): ResolvedTheme {
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
