import type { ReactNode } from 'react'
import { cn } from '@/utils'
import { Alert } from '@/components/ui'
import { useAlertStore } from '@/stores'
import { Navbar, type NavbarProps } from './Navbar.tsx'

interface AppLayoutProps {
  children: ReactNode
  navbarProps?: NavbarProps
  footer?: ReactNode
  className?: string
  mainClassName?: string
}

export function AppLayout({
  children,
  navbarProps,
  footer = true,
  className,
  mainClassName,
}: AppLayoutProps) {
  const { message, type, dismissAlert } = useAlertStore()

  return (
    <div
      className={cn(
        'min-h-screen bg-zinc-50 text-zinc-900 dark:bg-[#09090b] dark:text-zinc-100 flex flex-col antialiased selection:bg-primary-500/25 selection:text-primary-800 dark:selection:text-primary-200 transition-colors',
        className,
      )}
    >
      {/* Ambient radial glow: light & dark mode calibration */}
      <div className="fixed inset-0 pointer-events-none bg-[radial-gradient(ellipse_60%_50%_at_50%_0%,color-mix(in_srgb,var(--color-primary-500)_12%,transparent),transparent)] dark:bg-[radial-gradient(ellipse_60%_50%_at_50%_0%,color-mix(in_srgb,var(--color-primary-500)_8%,transparent),transparent)]" />

      {/* Top Navbar */}
      <Navbar {...navbarProps} />

      {/* Main Content Workspace */}
      <main
        className={cn(
          'relative z-10 flex-1 w-full mx-auto p-4 sm:p-6 lg:p-8 flex flex-col gap-4',
          mainClassName,
        )}
      >
        {/* Alert */}
        <Alert
          message={message ?? undefined}
          variant={type}
          onDismiss={dismissAlert}
        />

        {/* Children */}
        {children}
      </main>

      {/* Optional Page Footer */}
      {footer && (
        <footer className="relative z-10 w-full border-t border-zinc-200 dark:border-zinc-800/80 bg-white/70 dark:bg-zinc-950/40 text-xs text-zinc-600 dark:text-zinc-500">
          {footer}
        </footer>
      )}
    </div>
  )
}
