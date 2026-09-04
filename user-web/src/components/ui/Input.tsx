import { cn } from '../../lib/utils'
import type { InputHTMLAttributes, ReactNode, Ref } from 'react'

export interface InputProps extends InputHTMLAttributes<HTMLInputElement> {
  icon?: ReactNode
  ref?: Ref<HTMLInputElement>
}

// ponytail: native HTML input wrapper with light/dark theme & optional prefix icon
export function Input({ icon, className = '', ref, ...props }: InputProps) {
  if (icon) {
    return (
      <div className="flex items-center gap-2 bg-white/80 dark:bg-zinc-950/70 border border-zinc-200 dark:border-zinc-800/80 rounded-lg px-3 py-1.5 focus-within:border-zinc-400 dark:focus-within:border-zinc-600 focus-within:ring-1 focus-within:ring-zinc-400 dark:focus-within:ring-zinc-600 transition">
        {icon}
        <input
          ref={ref}
          className={cn(
            'w-full bg-transparent text-xs sm:text-sm text-zinc-900 dark:text-zinc-200 placeholder:text-zinc-400 dark:placeholder:text-zinc-600 focus:outline-none',
            className
          )}
          {...props}
        />
      </div>
    )
  }

  return (
    <input
      ref={ref}
      className={cn(
        'w-full bg-white/80 dark:bg-zinc-950/70 border border-zinc-200 dark:border-zinc-800/80 hover:border-zinc-300 dark:hover:border-zinc-700 rounded-lg px-3 py-1.5 text-xs sm:text-sm text-zinc-900 dark:text-zinc-200 placeholder:text-zinc-400 dark:placeholder:text-zinc-600 focus:outline-none focus:border-zinc-400 dark:focus:border-zinc-600 focus:ring-1 focus:ring-zinc-400 dark:focus:ring-zinc-600 transition',
        className
      )}
      {...props}
    />
  )
}
