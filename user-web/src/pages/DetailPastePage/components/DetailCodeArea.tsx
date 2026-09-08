import { SpinnerIcon } from '@/components/icons'

interface DetailCodeAreaProps {
  content: string
  isRaw?: boolean
  loading?: boolean
}

export function DetailCodeArea({
  content,
  isRaw = false,
  loading = false,
}: DetailCodeAreaProps) {
  if (loading) {
    return (
      <div className="flex-1 flex items-center justify-center min-h-105 text-zinc-400">
        <SpinnerIcon className="h-6 w-6 animate-spin text-primary" />
      </div>
    )
  }

  if (isRaw) {
    return (
      <div className="relative flex-1 flex flex-col p-4 sm:p-5 overflow-auto max-h-[72vh] min-h-105">
        <pre className="font-mono text-[13px] leading-6 text-zinc-900 dark:text-zinc-100 whitespace-pre selection:bg-primary-500/20">
          {content}
        </pre>
      </div>
    )
  }

  // ponytail: two <pre> nodes beat a <tr> per line
  const lineCount = content.split('\n').length
  const gutter = Array.from({ length: lineCount }, (_, i) => i + 1).join('\n')

  return (
    <div className="relative flex-1 flex overflow-auto max-h-[72vh] min-h-105">
      <pre className="sticky left-0 w-12 py-0.5 px-3 text-right text-zinc-400 dark:text-zinc-600 select-none text-xs leading-6 font-mono border-r border-zinc-200/80 dark:border-zinc-800/80 bg-white/90 dark:bg-zinc-900/90 shrink-0">
        {gutter}
      </pre>
      <pre className="flex-1 py-0.5 px-4 font-mono text-[13px] leading-6 text-zinc-900 dark:text-zinc-100 whitespace-pre selection:bg-primary-500/20">
        {content}
      </pre>
    </div>
  )
}
