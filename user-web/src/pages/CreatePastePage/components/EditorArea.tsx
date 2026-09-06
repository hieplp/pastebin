import { type KeyboardEvent } from 'react'
import type { DraftPaste } from '@/types'

interface EditorAreaProps {
  draft: DraftPaste
  setDraft: (
    updater: Partial<DraftPaste> | ((prev: DraftPaste) => DraftPaste),
  ) => void
  onSubmit?: () => void
}

export function EditorArea({ draft, setDraft, onSubmit }: EditorAreaProps) {
  /* ---- Functions ---- */
  const handleKeyDown = (e: KeyboardEvent<HTMLTextAreaElement>) => {
    if (e.key === 'Tab') {
      e.preventDefault()
      const textarea = e.currentTarget
      const start = textarea.selectionStart
      const end = textarea.selectionEnd
      setDraft((prev) => ({
        ...prev,
        content:
          prev.content.substring(0, start) + '  ' + prev.content.substring(end),
      }))
      requestAnimationFrame(() => {
        textarea.selectionStart = textarea.selectionEnd = start + 2
      })
      return
    }

    if ((e.metaKey || e.ctrlKey) && e.key === 'Enter') {
      e.preventDefault()
      onSubmit?.()
    }
  }

  return (
    <div className="relative flex-1 flex flex-col">
      <textarea
        value={draft.content}
        onChange={(e) =>
          setDraft((prev) => ({ ...prev, content: e.target.value }))
        }
        onKeyDown={handleKeyDown}
        placeholder="Paste or write your text or code here... (or drag & drop files)"
        spellCheck={false}
        className="flex-1 w-full p-4 sm:p-5 bg-transparent resize-y min-h-105 font-mono text-[13px] leading-6 text-zinc-900 dark:text-zinc-100 placeholder:text-zinc-400 dark:placeholder:text-zinc-650 focus:outline-none selection:bg-primary-500/20"
      />
    </div>
  )
}
