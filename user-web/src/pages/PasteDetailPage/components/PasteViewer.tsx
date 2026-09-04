import type { PasteItem } from '@/api/types'
import { CheckIcon, CopyIcon } from '@/components/icons'

export interface PasteViewerProps {
  paste: PasteItem
  lines: string[]
  showRaw: boolean
  copiedContent: boolean
  onToggleRaw: () => void
  onCopyContent: () => void | Promise<void>
}

// ponytail: paste code/text content viewer with line numbers, raw view toggle, and copy action
export function PasteViewer({
  paste,
  lines,
  showRaw,
  copiedContent,
  onToggleRaw,
  onCopyContent,
}: PasteViewerProps) {
  return (
    <div className="rounded-2xl border border-zinc-200 dark:border-zinc-800 bg-white/70 dark:bg-zinc-900/40 backdrop-blur-xl shadow-xl dark:shadow-2xl shadow-zinc-950/5 dark:shadow-black/40 overflow-hidden flex flex-col">
      {/* Header / Info Bar */}
      <div className="flex flex-wrap items-center justify-between gap-3 px-4 sm:px-6 py-3.5 border-b border-zinc-200 dark:border-zinc-800 bg-zinc-50/70 dark:bg-zinc-900/70">
        <div className="flex items-center gap-2.5 min-w-0">
          <h1 className="font-semibold text-base text-zinc-900 dark:text-zinc-100 truncate">
            {paste.title || 'Untitled'}
          </h1>
          {paste.alias && (
            <span className="px-2 py-0.5 rounded-md bg-primary-500/10 text-primary-700 dark:text-primary-300 text-xs font-mono border border-primary-500/20">
              /{paste.alias}
            </span>
          )}
        </div>

        <div className="flex flex-wrap items-center gap-2 text-xs">
          <span className="px-2 py-1 rounded-md bg-zinc-100 dark:bg-zinc-800 text-zinc-600 dark:text-zinc-400 font-mono">
            {paste.syntax || 'plaintext'}
          </span>
          <span className="px-2 py-1 rounded-md bg-zinc-100 dark:bg-zinc-800 text-zinc-500 dark:text-zinc-400">
            {lines.length} {lines.length === 1 ? 'line' : 'lines'}
          </span>
          {paste.expiredAt && (
            <span className="px-2 py-1 rounded-md bg-amber-500/10 text-amber-600 dark:text-amber-400 border border-amber-500/20">
              Expires {new Date(paste.expiredAt).toLocaleDateString()}
            </span>
          )}

          <div className="h-4 w-px bg-zinc-200 dark:bg-zinc-800 mx-0.5" />

          <button
            type="button"
            onClick={onToggleRaw}
            className="px-2.5 py-1 rounded-md border border-zinc-200 dark:border-zinc-750 bg-white dark:bg-zinc-800 hover:bg-zinc-50 dark:hover:bg-zinc-750 text-zinc-700 dark:text-zinc-300 transition cursor-pointer"
          >
            {showRaw ? 'Formatted' : 'Raw'}
          </button>

          <button
            type="button"
            onClick={() => void onCopyContent()}
            className="px-2.5 py-1 rounded-md border border-zinc-200 dark:border-zinc-750 bg-white dark:bg-zinc-800 hover:bg-zinc-50 dark:hover:bg-zinc-750 text-zinc-700 dark:text-zinc-300 flex items-center gap-1.5 transition cursor-pointer"
          >
            {copiedContent ? (
              <>
                <CheckIcon className="w-3.5 h-3.5 text-emerald-500" />
                <span className="text-emerald-600 dark:text-emerald-400 font-medium">Copied</span>
              </>
            ) : (
              <>
                <CopyIcon className="w-3.5 h-3.5" />
                <span>Copy Content</span>
              </>
            )}
          </button>
        </div>
      </div>

      {/* Code / Content Area */}
      {showRaw ? (
        <textarea
          readOnly
          value={paste.content}
          className="w-full p-4 sm:p-5 font-mono text-[13px] leading-6 bg-transparent text-zinc-900 dark:text-zinc-100 min-h-[300px] resize-y focus:outline-none"
        />
      ) : (
        <div className="overflow-x-auto p-4 sm:p-5 font-mono text-[13px] leading-6 flex min-h-[250px]">
          {/* Line numbers */}
          <div className="select-none pr-4 text-right text-zinc-400 dark:text-zinc-600 border-r border-zinc-200 dark:border-zinc-800 shrink-0 font-mono text-xs leading-6">
            {lines.map((_, i) => (
              <div key={i}>{i + 1}</div>
            ))}
          </div>
          {/* Code text */}
          <pre className="pl-4 flex-1 text-zinc-900 dark:text-zinc-100 whitespace-pre overflow-x-auto selection:bg-primary-500/20">
            <code>{paste.content}</code>
          </pre>
        </div>
      )}
    </div>
  )
}
