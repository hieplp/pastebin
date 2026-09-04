export interface PasteNotFoundProps {
  message?: string
}

// ponytail: paste not found / expired error state card with link to create new paste
export function PasteNotFound({ message }: PasteNotFoundProps) {
  return (
    <div className="flex-1 flex flex-col items-center justify-center min-h-[300px] gap-4 text-center p-6 rounded-2xl border border-zinc-200 dark:border-zinc-800 bg-white/70 dark:bg-zinc-900/40 backdrop-blur-xl">
      <div className="h-12 w-12 rounded-2xl bg-destructive-500/10 border border-destructive-500/20 flex items-center justify-center text-destructive-500 text-xl font-bold">
        !
      </div>
      <div className="space-y-1">
        <h2 className="text-base font-semibold text-zinc-900 dark:text-zinc-100">Paste not found</h2>
        <p className="text-xs text-zinc-500 dark:text-zinc-400">
          {message || 'This paste may have expired or does not exist.'}
        </p>
      </div>
      <a
        href="/"
        className="px-4 py-2 rounded-xl bg-primary-600 hover:bg-primary-500 text-white text-xs font-semibold transition shadow-sm active:scale-[0.98]"
      >
        Create a New Paste
      </a>
    </div>
  )
}
