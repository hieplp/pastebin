import { cn } from '@/utils'
import { SpinnerIcon } from '@/components/icons'
import type { ButtonHTMLAttributes, ReactNode } from 'react'

export interface LoadingButtonProps extends ButtonHTMLAttributes<HTMLButtonElement> {
  loading?: boolean
  loadingText?: ReactNode
}

// submit button with spinner state, shortcut indicator, and theme styling
export function LoadingButton({
  type = 'button',
  loading,
  loadingText = 'Creating...',
  disabled,
  className = '',
  children,
  ...props
}: LoadingButtonProps) {
  return (
    <button
      type={type}
      disabled={disabled || loading}
      className={cn('btn-primary', className)}
      {...props}
    >
      {loading ? (
        <>
          <SpinnerIcon className="animate-spin h-4 w-4 text-zinc-950" />
          <span>{loadingText}</span>
        </>
      ) : (
        (children ?? (
          <>
            <span>Create Paste</span>
            <span className="text-xs opacity-75 font-mono">↵</span>
          </>
        ))
      )}
    </button>
  )
}
