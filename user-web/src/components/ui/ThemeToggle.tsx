import { cn } from '@/utils'
import { useTheme } from '@/stores'
import { SunIcon, MoonIcon } from '../icons'

export interface ThemeToggleProps {
  className?: string
}

// single-click toggle between light and dark with accessible aria label
export function ThemeToggle({ className = '' }: ThemeToggleProps) {
  const { resolvedTheme, toggleTheme } = useTheme()
  const isDark = resolvedTheme === 'dark'

  return (
    <button
      type="button"
      onClick={toggleTheme}
      className={cn(
        'inline-flex items-center justify-center p-1.5 rounded-lg text-zinc-500 hover:text-zinc-900 hover:bg-zinc-100 dark:text-zinc-400 dark:hover:text-zinc-100 dark:hover:bg-zinc-800/80 transition cursor-pointer focus:outline-none focus-visible:ring-2 focus-visible:ring-primary-500/50',
        className,
      )}
      aria-label={`Switch to ${isDark ? 'light' : 'dark'} mode`}
      title={`Switch to ${isDark ? 'light' : 'dark'} mode`}
    >
      {isDark ? (
        <SunIcon className="h-4 w-4" />
      ) : (
        <MoonIcon className="h-4 w-4" />
      )}
    </button>
  )
}
