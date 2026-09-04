import { cn } from '../../lib/utils'
import { SpinnerIcon } from '../icons'
import type { ButtonHTMLAttributes, MouseEventHandler, ReactNode } from 'react'

export interface LoadingButtonProps extends Omit<ButtonHTMLAttributes<HTMLButtonElement>, 'onSubmit'> {
  loading?: boolean
  submitting?: boolean
  canSubmit?: boolean
  loadingText?: ReactNode
  onSubmit?: MouseEventHandler<HTMLButtonElement> | (() => void)
}

// ponytail: submit button with spinner state, shortcut indicator, and theme styling
export function LoadingButton({
  type = 'button',
  loading,
  submitting,
  canSubmit,
  loadingText = 'Creating...',
  onSubmit,
  onClick,
  disabled,
  className = '',
  children,
  ...props
}: LoadingButtonProps) {
  const isLoading = Boolean(loading ?? submitting)
  const isDisabled = Boolean(disabled || (canSubmit !== undefined ? !canSubmit : false) || isLoading)

  return (
    <button
      type={type}
      onClick={onClick ?? onSubmit}
      disabled={isDisabled}
      className={cn('btn-primary', className)}
      {...props}
    >
      {isLoading ? (
        <>
          <SpinnerIcon className="animate-spin h-4 w-4 text-zinc-950" />
          <span>{loadingText}</span>
        </>
      ) : (
        children ?? (
          <>
            <span>Create Paste</span>
            <span className="text-xs opacity-75 font-mono">↵</span>
          </>
        )
      )}
    </button>
  )
}
