import type { RefObject } from 'react'
import { CheckIcon, CopyIcon } from '@/components/icons'
import { cn } from '@/lib/utils'

export interface ShareUrlCardProps {
  shareUrl: string
  copied: boolean
  inputRef?: RefObject<HTMLInputElement | null>
  onCopy: () => void | Promise<void>
}

// ponytail: share card component with high-visibility URL display and copy button
export function ShareUrlCard({ shareUrl, copied, inputRef, onCopy }: ShareUrlCardProps) {
  return (
    <div className="rounded-2xl border border-primary-500/30 bg-primary-500/5 dark:bg-primary-500/10 p-4 sm:p-5 backdrop-blur-xl shadow-lg space-y-3">
      <div className="flex items-center justify-between gap-2">
        <div className="flex items-center gap-2">
          <span className="text-xs font-semibold uppercase tracking-wider text-primary-700 dark:text-primary-400">
            Share URL
          </span>
          <span className="hidden sm:inline text-xs text-zinc-500 dark:text-zinc-400">
            — Anyone with this link can view this paste
          </span>
        </div>
        {copied && (
          <span className="inline-flex items-center gap-1 text-xs font-semibold text-emerald-600 dark:text-emerald-400 animate-in fade-in duration-200">
            <CheckIcon className="w-4 h-4" />
            Copied to clipboard!
          </span>
        )}
      </div>

      <div className="flex items-center gap-2">
        <div className="relative flex-1">
          <input
            ref={inputRef}
            type="text"
            readOnly
            value={shareUrl}
            onClick={(e) => e.currentTarget.select()}
            title="Click to select URL"
            className="w-full px-3.5 py-2.5 rounded-xl border border-zinc-200 dark:border-zinc-800 bg-white/90 dark:bg-zinc-900/90 text-zinc-900 dark:text-zinc-100 font-mono text-xs sm:text-sm focus:outline-none focus:ring-2 focus:ring-primary-500/40 selection:bg-primary-500/30 cursor-pointer"
          />
        </div>

        <button
          type="button"
          onClick={() => void onCopy()}
          className={cn(
            'px-4 py-2.5 rounded-xl text-xs sm:text-sm font-semibold flex items-center gap-2 transition-all shadow-sm cursor-pointer shrink-0 active:scale-[0.98]',
            copied
              ? 'bg-emerald-600 text-white shadow-emerald-500/20'
              : 'bg-primary-600 hover:bg-primary-500 text-white shadow-primary-600/20'
          )}
        >
          {copied ? (
            <>
              <CheckIcon className="w-4 h-4" />
              <span>Copied!</span>
            </>
          ) : (
            <>
              <CopyIcon className="w-4 h-4" />
              <span>Copy URL</span>
            </>
          )}
        </button>
      </div>
    </div>
  )
}
