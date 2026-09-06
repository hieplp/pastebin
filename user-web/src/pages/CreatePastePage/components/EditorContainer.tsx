import { useDragDrop } from '@/hooks'
import { cn } from '@/utils'
import { UploadIcon } from '@/components/icons'
import type { ReactNode } from 'react'
import type { DraftPaste } from '@/types'

/* ---- DragOverlay ---- */

interface DragOverlayProps {
  visible?: boolean
}

function DragOverlay({ visible = true }: DragOverlayProps) {
  if (!visible) return null

  return (
    <div className="absolute inset-0 z-20 flex flex-col items-center justify-center bg-white/85 dark:bg-zinc-950/85 backdrop-blur-sm rounded-2xl pointer-events-none transition-all">
      <div className="h-12 w-12 rounded-2xl bg-primary-500/10 border border-primary-500/30 flex items-center justify-center text-primary mb-3 shadow-lg shadow-primary-500/10">
        <UploadIcon className="h-6 w-6" />
      </div>
      <span className="text-primary-300 font-semibold text-base">
        Drop files here to load content
      </span>
      <span className="text-zinc-400 text-xs mt-1">
        Accepts code, logs, and plain text files
      </span>
    </div>
  )
}

/* ---- EditorStatusBar ---- */

interface EditorStatusBarProps {
  content: string
  syntax: string
  className?: string
}

function EditorStatusBar({ content, syntax, className }: EditorStatusBarProps) {
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
        <span>Tab = 2 spaces</span>
        <span>•</span>
        <span className="text-primary/80">{syntax}</span>
      </div>
    </div>
  )
}

/* ---- EditorContainer ---- */

interface EditorContainerProps {
  children: ReactNode
  draft: DraftPaste
  onDropFiles: (files: FileList) => void
}

export function EditorContainer({
  children,
  draft,
  onDropFiles,
}: EditorContainerProps) {
  /* ---- Hooks ---- */
  const { isDragging, dragProps } = useDragDrop({ onDropFiles })

  /* ---- Render ---- */
  return (
    <div
      {...dragProps}
      className={cn(
        'group relative flex-1 flex flex-col rounded-2xl border transition-all duration-200 shadow-2xl shadow-black/40 overflow-hidden',
        isDragging
          ? 'border-primary-500 bg-primary-950/20 ring-4 ring-primary-500/20'
          : 'border-zinc-200 dark:border-zinc-800 bg-white/70 dark:bg-zinc-900/40 hover:border-zinc-300 dark:hover:border-zinc-700/80 backdrop-blur-xl shadow-xl dark:shadow-2xl shadow-zinc-950/5 dark:shadow-black/40',
      )}
    >
      {/* Drag Overlay Indicator */}
      <DragOverlay visible={isDragging} />

      {/* Children */}
      {children}

      {/* Editor Status Footer */}
      <EditorStatusBar
        content={draft.content}
        syntax={draft.syntax}
        className="mt-auto"
      />
    </div>
  )
}
