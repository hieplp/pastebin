import { cn } from '@/utils'
import type { ReactNode } from 'react'

/* ---- DetailStatusBar ---- */

interface DetailStatusBarProps {
  content: string
  syntax: string
  createdAt?: string
  className?: string
}

function DetailStatusBar({
  content,
  syntax,
  createdAt,
  className,
}: DetailStatusBarProps) {
  const lineCount = content ? content.split('\n').length : 0
  const charCount = content.length
  const byteCount = new Blob([content]).size

  return (
    <div
      className={cn(
        'border-t border-zinc-200 dark:border-zinc-800/80 px-4 py-2.5 flex flex-wrap items-center justify-between text-xs text-zinc-500 font-mono bg-zinc-50/70 dark:bg-zinc-950/60',
        className,
      )}
    >
      <div className="flex items-center gap-4">
        <span>
          {lineCount} {lineCount === 1 ? 'line' : 'lines'}
        </span>
        <span>•</span>
        <span>{charCount} characters</span>
        {byteCount > 0 && (
          <>
            <span>•</span>
            <span>{(byteCount / 1024).toFixed(1)} KB</span>
          </>
        )}
      </div>
      <div className="flex items-center gap-3">
        {createdAt && (
          <>
            <span>
              Created{' '}
              {new Date(createdAt).toLocaleDateString(undefined, {
                year: 'numeric',
                month: 'short',
                day: 'numeric',
              })}
            </span>
            <span>•</span>
          </>
        )}
        <span className="text-primary/80">{syntax}</span>
      </div>
    </div>
  )
}

/* ---- DetailContainer ---- */

interface DetailContainerProps {
  children: ReactNode
  content: string
  syntax?: string
  createdAt?: string
  className?: string
}

export function DetailContainer({
  children,
  content,
  syntax = 'plaintext',
  createdAt,
  className,
}: DetailContainerProps) {
  /* ---- Render ---- */
  return (
    <div
      className={cn(
        'group relative flex-1 flex flex-col rounded-2xl border transition-all duration-200 shadow-2xl shadow-black/40 overflow-hidden',
        'border-zinc-200 dark:border-zinc-800 bg-white/70 dark:bg-zinc-900/40 hover:border-zinc-300 dark:hover:border-zinc-700/80 backdrop-blur-xl shadow-xl dark:shadow-2xl shadow-zinc-950/5 dark:shadow-black/40',
        className,
      )}
    >
      {/* Container Content (Toolbar, Files, Code Area) */}
      {children}

      {/* Status Footer */}
      <DetailStatusBar
        content={content}
        syntax={syntax}
        createdAt={createdAt}
        className="mt-auto"
      />
    </div>
  )
}
