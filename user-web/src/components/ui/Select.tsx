import { cn } from '@/utils'
import { useState, useRef, type ReactNode } from 'react'
import { useClickOutside, useShortcut } from '@/hooks'
import { ChevronDownIcon, CheckIcon } from '@/components/icons'
import type { SelectOption } from '@/types'

export interface SelectProps {
  value?: string
  onChange?: (e: { target: { value: string } }) => void
  options?: SelectOption[]
  placeholder?: string
  className?: string
  children?: ReactNode
}

// custom dark-theme dropdown with zero extra dependencies; click-outside & escape support
export function Select({
  value,
  onChange,
  options,
  placeholder = 'Select an option',
  className = '',
  children,
}: SelectProps) {
  const [isOpen, setIsOpen] = useState(false)
  const containerRef = useRef<HTMLDivElement>(null)

  useClickOutside(containerRef, () => setIsOpen(false), isOpen)
  useShortcut('Escape', () => setIsOpen(false), { enabled: isOpen })

  // Fallback to native select if only children are provided
  if (!options && children) {
    return (
      <div className="relative inline-block">
        <select
          value={value}
          onChange={(e) => onChange?.(e)}
          className={cn(
            'appearance-none bg-white/80 dark:bg-zinc-950/70 border border-zinc-200 dark:border-zinc-800/80 hover:border-zinc-300 dark:hover:border-zinc-700 rounded-lg pl-3 pr-8 py-1.5 text-xs text-zinc-700 dark:text-zinc-300 focus:outline-none focus:border-zinc-400 dark:focus:border-zinc-600 focus:ring-1 focus:ring-zinc-400 dark:focus:ring-zinc-600 cursor-pointer transition',
            className,
          )}
        >
          {children}
        </select>
        <span className="pointer-events-none absolute right-2.5 top-1/2 -translate-y-1/2 text-zinc-500 text-[10px]">
          ▼
        </span>
      </div>
    )
  }

  const selectedOption = options?.find((opt) => opt.value === value)

  return (
    <div ref={containerRef} className="relative inline-block">
      {/* Dropdown Trigger */}
      <button
        type="button"
        onClick={() => setIsOpen((prev) => !prev)}
        className={cn(
          'inline-flex items-center justify-between gap-2.5 bg-white/80 dark:bg-zinc-950/70 border border-zinc-200 dark:border-zinc-800/80 hover:border-zinc-300 dark:hover:border-zinc-700 hover:bg-zinc-50 dark:hover:bg-zinc-900/60 rounded-lg px-3 py-1.5 text-xs sm:text-sm text-zinc-800 dark:text-zinc-200 transition cursor-pointer focus:outline-none focus:border-zinc-400 dark:focus:border-zinc-600 focus:ring-1 focus:ring-zinc-400 dark:focus:ring-zinc-600',
          className,
        )}
      >
        <span className="truncate font-medium">
          {selectedOption?.label ?? placeholder}
        </span>
        <ChevronDownIcon
          className={cn(
            'h-3 w-3 text-zinc-400 transition-transform duration-150 shrink-0',
            isOpen && 'rotate-180 text-primary',
          )}
        />
      </button>

      {/* Floating Options Panel */}
      {isOpen && options && (
        <div className="absolute left-0 z-50 mt-1.5 min-w-42.5 max-h-52 overflow-y-auto rounded-xl border border-zinc-200 dark:border-zinc-800/90 bg-white/95 dark:bg-zinc-900/95 p-1 shadow-xl dark:shadow-2xl shadow-zinc-950/10 dark:shadow-black/80 backdrop-blur-md ring-1 ring-black/5 dark:ring-white/10 flex flex-col gap-0.5 [scrollbar-width:thin] [scrollbar-color:#3f3f46_transparent] [&::-webkit-scrollbar]:w-1 [&::-webkit-scrollbar-track]:bg-transparent [&::-webkit-scrollbar-thumb]:bg-zinc-300 dark:[&::-webkit-scrollbar-thumb]:bg-zinc-700/70 [&::-webkit-scrollbar-thumb]:rounded-full hover:[&::-webkit-scrollbar-thumb]:bg-zinc-400 dark:hover:[&::-webkit-scrollbar-thumb]:bg-zinc-600">
          {options.map((opt) => {
            const isSelected = opt.value === value
            return (
              <button
                key={opt.value}
                type="button"
                onClick={() => {
                  onChange?.({ target: { value: opt.value } })
                  setIsOpen(false)
                }}
                className={cn(
                  'w-full flex items-center justify-between gap-3 px-2.5 py-1.5 rounded-lg text-xs transition cursor-pointer text-left',
                  isSelected
                    ? 'bg-primary/15 text-primary font-medium'
                    : 'text-zinc-700 dark:text-zinc-300 hover:bg-zinc-100 dark:hover:bg-zinc-800 hover:text-zinc-900 dark:hover:text-zinc-100',
                )}
              >
                <span className="truncate">{opt.label}</span>
                {isSelected && (
                  <CheckIcon
                    className="h-3.5 w-3.5 text-primary shrink-0"
                    strokeWidth={2.5}
                  />
                )}
              </button>
            )
          })}
        </div>
      )}
    </div>
  )
}
