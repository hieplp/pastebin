import type { ReactNode } from 'react'
import { cn } from '@/utils'
import { CheckIcon, CloseIcon } from '@/components/icons'

export type AlertVariant = 'success' | 'error' | 'warning' | 'info'

export interface AlertProps {
  children?: ReactNode
  message?: string
  variant?: AlertVariant
  type?: AlertVariant
  onDismiss?: () => void
  className?: string
}

const VARIANT_CONFIG: Record<
  AlertVariant,
  {
    container: string
    iconColor: string
    dismiss: string
    icon: typeof CheckIcon
  }
> = {
  success: {
    container:
      'bg-primary-50 dark:bg-primary-950/60 border-primary-500/30 text-primary-900 dark:text-primary-300 shadow-primary-950/10 dark:shadow-primary-950/30',
    iconColor: 'text-primary',
    dismiss:
      'text-primary-700 dark:text-primary hover:text-primary-900 dark:hover:text-primary-200',
    icon: CheckIcon,
  },
  error: {
    container:
      'bg-destructive-50 dark:bg-destructive-950/60 border-destructive-500/30 text-destructive-900 dark:text-destructive-300 shadow-destructive-950/10 dark:shadow-destructive-950/30',
    iconColor: 'text-destructive',
    dismiss:
      'text-destructive-700 dark:text-destructive hover:text-destructive-900 dark:hover:text-destructive-200',
    icon: CloseIcon,
  },
  warning: {
    container:
      'bg-amber-50 dark:bg-amber-950/60 border-amber-500/30 text-amber-900 dark:text-amber-300 shadow-amber-950/10 dark:shadow-amber-950/30',
    iconColor: 'text-amber-500',
    dismiss:
      'text-amber-700 dark:text-amber-400 hover:text-amber-900 dark:hover:text-amber-200',
    icon: CloseIcon,
  },
  info: {
    container:
      'bg-accent-50 dark:bg-accent-950/60 border-accent-500/30 text-accent-900 dark:text-accent-300 shadow-accent-950/10 dark:shadow-accent-950/30',
    iconColor: 'text-accent',
    dismiss:
      'text-accent-700 dark:text-accent hover:text-accent-900 dark:hover:text-accent-200',
    icon: CheckIcon,
  },
}

// ponytail: alert banner with dismiss action and variant styling
export function Alert({
  children,
  message,
  variant,
  type,
  onDismiss,
  className = '',
}: AlertProps) {
  const content = children ?? message
  if (!content) return null

  const activeVariant = variant ?? type ?? 'success'
  const config = VARIANT_CONFIG[activeVariant] ?? VARIANT_CONFIG.success
  const Icon = config.icon

  return (
    <div
      className={cn(
        'p-3.5 rounded-xl border text-sm flex items-center justify-between shadow-lg backdrop-blur',
        config.container,
        className,
      )}
    >
      <div className="flex items-center gap-2.5">
        <Icon className={cn('h-4 w-4 shrink-0', config.iconColor)} />
        <span>{content}</span>
      </div>
      {onDismiss && (
        <button
          type="button"
          onClick={onDismiss}
          className={cn(
            'text-xs font-medium cursor-pointer ml-4',
            config.dismiss,
          )}
        >
          Dismiss
        </button>
      )}
    </div>
  )
}
