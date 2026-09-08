import { CheckIcon } from '@/components/icons'
import { CopyButton } from '@/components/ui'

interface CreatedPasteCardProps {
  pasteId: string
  alias?: string
  onCreateAnother: () => void
}

// success panel shown after a paste is created: copyable share link, no redirect
export function CreatedPasteCard({
  pasteId,
  alias,
  onCreateAnother,
}: CreatedPasteCardProps) {
  const url = `${window.location.origin}/pastes/${alias || pasteId}`

  return (
    <div className="w-full max-w-lg rounded-2xl border border-zinc-200 dark:border-zinc-800 bg-white/70 dark:bg-zinc-900/40 backdrop-blur-xl shadow-2xl shadow-zinc-950/10 dark:shadow-black/40 p-8 flex flex-col items-center text-center">
      {/* Success icon */}
      <div className="h-12 w-12 rounded-2xl bg-primary-500/10 border border-primary-500/30 flex items-center justify-center text-primary mb-4 shadow-lg shadow-primary-500/10">
        <CheckIcon className="h-6 w-6" />
      </div>

      {/* Heading */}
      <h1 className="text-lg font-semibold">Paste created!</h1>
      <p className="text-sm text-zinc-500 dark:text-zinc-400 mt-1 mb-6">
        Your paste is ready — copy the link to share it.
      </p>

      {/* Link + Copy */}
      <div className="w-full flex items-center gap-2 rounded-xl border border-zinc-200 dark:border-zinc-800 bg-zinc-50/70 dark:bg-zinc-950/60 py-1.5 pl-3 pr-1.5">
        <span className="flex-1 truncate text-sm font-mono text-zinc-600 dark:text-zinc-400 text-left">
          {url}
        </span>
        <CopyButton
          text={url}
          label="Copy link"
          title="Copy paste link"
          className="shrink-0"
        />
      </div>

      {/* Create another */}
      <button
        type="button"
        onClick={onCreateAnother}
        className="btn-ghost mt-6"
      >
        ← Create another paste
      </button>
    </div>
  )
}
