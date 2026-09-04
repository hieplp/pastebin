import { cn } from '@/lib/utils'

export interface EditorStatusBarProps {
  content: string
  syntax: string
  className?: string
}

// ponytail: status bar calculating line, character, and byte metrics natively
export function EditorStatusBar({ content, syntax, className }: EditorStatusBarProps) {
  const lineCount = content ? content.split('\n').length : 0
  const charCount = content.length
  const byteCount = new Blob([content]).size

  return (
    <div
      className={cn('border-t border-zinc-200 dark:border-zinc-800/80 px-4 py-2.5 flex flex-wrap items-center justify-between text-xs text-zinc-500 font-mono bg-zinc-50/70 dark:bg-zinc-950/60', className)}
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
        <span>Tab = 2 spaces</span>
        <span>•</span>
        <span className="text-primary/80">{syntax}</span>
      </div>
    </div>
  )
}
