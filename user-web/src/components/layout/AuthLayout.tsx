import type { ReactNode } from 'react'
import { cn } from '@/lib/utils'
import { ThemeToggle } from '../ui/ThemeToggle'

export interface AuthLayoutProps {
  children: ReactNode
  /** Card heading title */
  title?: string
  /** Subtitle or description below the title */
  description?: string
  /** Optional custom brand logo element */
  brand?: ReactNode
  /** Whether to show the theme toggle in the top corner. Defaults to true */
  showThemeToggle?: boolean
  /** Optional footer content below the card */
  footer?: ReactNode
  /** Additional container classes */
  className?: string
}

// ponytail: centered layout shell for authentication screens with light/dark styling
export function AuthLayout({
  children,
  title,
  description,
  brand,
  showThemeToggle = true,
  footer,
  className,
}: AuthLayoutProps) {
  return (
    <div
      className={cn('min-h-screen bg-zinc-50 text-zinc-900 dark:bg-[#09090b] dark:text-zinc-100 flex flex-col items-center justify-center p-4 sm:p-6 antialiased selection:bg-primary-500/25 selection:text-primary-800 dark:selection:text-primary-200 transition-colors', className)}
    >
      {/* Ambient radial glow */}
      <div className="fixed inset-0 pointer-events-none bg-[radial-gradient(ellipse_60%_50%_at_50%_0%,color-mix(in_srgb,var(--color-primary-500)_12%,transparent),transparent)] dark:bg-[radial-gradient(ellipse_60%_50%_at_50%_0%,color-mix(in_srgb,var(--color-primary-500)_8%,transparent),transparent)]" />

      {/* Top right theme toggle */}
      {showThemeToggle && (
        <div className="absolute top-4 right-4 z-20">
          <ThemeToggle />
        </div>
      )}

      <div className="relative z-10 w-full max-w-md flex flex-col items-center">
        {/* Brand header */}
        <div className="mb-8">
          {brand ?? (
            <a href="/" className="flex items-center gap-2 hover:opacity-90 transition">
              <div className="h-8 w-8 rounded-xl bg-primary-500/10 border border-primary-500/20 flex items-center justify-center text-primary-700 dark:text-primary font-bold text-base shadow-inner">
                ⚡
              </div>
              <span className="font-semibold text-lg tracking-tight text-zinc-900 dark:text-white">pastebin</span>
            </a>
          )}
        </div>

        {/* Auth card */}
        <div className="w-full rounded-2xl border border-zinc-200 dark:border-zinc-800 bg-white/80 dark:bg-zinc-900/40 p-6 sm:p-8 backdrop-blur-xl shadow-xl dark:shadow-2xl shadow-zinc-950/5 dark:shadow-black/40">
          {(title || description) && (
            <div className="text-center mb-6">
              {title && <h1 className="text-xl font-semibold text-zinc-900 dark:text-zinc-100">{title}</h1>}
              {description && <p className="text-sm text-zinc-500 dark:text-zinc-400 mt-1">{description}</p>}
            </div>
          )}
          {children}
        </div>

        {/* Optional footer */}
        {footer && (
          <div className="mt-6 text-center text-xs text-zinc-500">
            {footer}
          </div>
        )}
      </div>
    </div>
  )
}
