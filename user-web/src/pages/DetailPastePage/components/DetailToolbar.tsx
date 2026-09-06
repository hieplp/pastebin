import { DocumentIcon } from '@/components/icons'
import { CopyButton, DownloadButton } from '@/components/ui'
import type { Paste } from '@/types'

interface DetailToolbarProps {
  paste: Paste
  onToggleRaw: () => void
  isRaw: boolean
}

export function DetailToolbar({
  paste,
  onToggleRaw,
  isRaw,
}: DetailToolbarProps) {
  /* ---- States ---- */
  const isExpired = paste.expiredAt && new Date(paste.expiredAt) < new Date()

  /* ---- Render ---- */
  return (
    <div className="sticky top-0 z-10 border-b border-zinc-200/90 dark:border-zinc-800/90 bg-zinc-50/90 dark:bg-zinc-900/90 backdrop-blur-md p-3 sm:px-4 flex flex-wrap items-center justify-between gap-3">
      {/* Title & Metadata */}
      <div className="flex items-center gap-2.5 min-w-0 flex-1">
        <DocumentIcon className="h-4 w-4 text-zinc-500 shrink-0" />
        <h1 className="font-medium text-sm text-zinc-900 dark:text-zinc-100 truncate">
          {paste.title || 'Untitled'}
        </h1>

        {/* Syntax Badge */}
        {paste.syntax && (
          <span className="badge-secondary shrink-0 text-[11px] py-0.5 px-2">
            {paste.syntax}
          </span>
        )}

        {/* Privacy Badge */}
        {paste.privacy && (
          <span className="badge-primary shrink-0">{paste.privacy}</span>
        )}

        {/* Expired Badge */}
        {isExpired && (
          <span className="inline-flex items-center px-2 py-0.5 rounded-full bg-destructive-500/10 border border-destructive-500/20 text-[11px] font-medium text-destructive-600 dark:text-destructive-400 shrink-0">
            Expired
          </span>
        )}
      </div>

      {/* Action Buttons */}
      <div className="flex items-center gap-1 flex-wrap">
        {/* Toggle Raw View */}
        <button
          type="button"
          onClick={onToggleRaw}
          title={isRaw ? 'Show formatted view' : 'Show raw text'}
          className="btn-secondary"
        >
          <span>{isRaw ? 'Formatted' : 'Raw'}</span>
        </button>

        {/* Copy Content */}
        <CopyButton text={paste.content} />

        {/* Download Button */}
        <DownloadButton
          content={paste.content}
          title={paste.title}
          syntax={paste.syntax || 'plaintext'}
        />
      </div>
    </div>
  )
}
