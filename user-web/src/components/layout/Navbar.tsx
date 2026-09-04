import type { ReactNode } from 'react'
import { cn } from '@/lib/utils'
import { ThemeToggle } from '../ui/ThemeToggle'

export interface NavbarProps {
  /** Custom brand element. Defaults to "⚡ pastebin" */
  brand?: ReactNode
  /** Optional badge text or element displayed next to brand */
  badge?: ReactNode
  /** Center / custom navigation items */
  children?: ReactNode
  /** Right-aligned actions (shortcuts, action buttons, links) */
  actions?: ReactNode
  /** Whether to show the theme toggle button. Defaults to true */
  showThemeToggle?: boolean
  /** Additional container classes */
  className?: string
}

// ponytail: reusable top navbar with light/dark styling, default brand, badge, and theme toggle
export function Navbar({
  brand,
  badge,
  children,
  actions,
  showThemeToggle = true,
  className,
}: NavbarProps) {
  return (
    <header
      className={cn('relative z-10 border-b border-zinc-200/80 dark:border-zinc-800/80 bg-white/80 dark:bg-zinc-950/70 backdrop-blur-md px-4 sm:px-6 py-3.5 flex items-center justify-between gap-4 transition-colors', className)}
    >
      <div className="flex items-center gap-3">
        {brand ?? (
          <a href="/" className="flex items-center gap-2 hover:opacity-90 transition">
            <div className="h-7 w-7 rounded-lg bg-primary-500/10 border border-primary-500/20 flex items-center justify-center text-primary-700 dark:text-primary font-bold text-sm shadow-inner">
              ⚡
            </div>
            <span className="font-semibold text-base tracking-tight text-zinc-900 dark:text-white">pastebin</span>
          </a>
        )}

        {badge && (
          <>
            <span className="h-4 w-px bg-zinc-200 dark:bg-zinc-800" />
            {typeof badge === 'string' ? (
              <span className="badge-primary">
                <span className="h-1.5 w-1.5 rounded-full bg-primary-600 dark:bg-primary animate-pulse" />
                {badge}
              </span>
            ) : (
              badge
            )}
          </>
        )}

        {children}
      </div>

      <div className="flex items-center gap-2">
        {actions}
        {showThemeToggle && <ThemeToggle />}
      </div>
    </header>
  )
}
