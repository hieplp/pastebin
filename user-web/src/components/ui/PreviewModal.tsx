import type { UploadedFile } from '@/types'
import { CloseIcon } from '@/components/icons'
import { useModal } from '@/hooks'

interface PreviewModalProps {
  file: UploadedFile
  onSelect?: (file: UploadedFile) => void
  onClose: () => void
}

export function PreviewModal({ file, onSelect, onClose }: PreviewModalProps) {
  useModal(onClose)
  return (
    <div
      className="fixed inset-0 z-50 flex items-center justify-center p-4 sm:p-6 bg-black/60 backdrop-blur-xs"
      onClick={onClose}
    >
      <div
        className="bg-white dark:bg-zinc-900 border border-zinc-200 dark:border-zinc-800 rounded-2xl max-w-3xl w-full max-h-[85vh] flex flex-col shadow-2xl overflow-hidden"
        onClick={(e) => e.stopPropagation()}
      >
        <div className="flex items-center justify-between px-4 py-3 border-b border-zinc-200 dark:border-zinc-800 bg-zinc-50/80 dark:bg-zinc-950/60">
          <div className="flex items-center gap-2 min-w-0">
            <span className="font-semibold text-sm truncate text-zinc-900 dark:text-zinc-100">
              {file.name}
            </span>
            <span className="text-xs text-zinc-500 font-mono">
              ({(file.size / 1024).toFixed(1)} KB)
            </span>
          </div>
          <div className="flex items-center gap-2">
            {onSelect && file.content !== undefined && (
              <button
                type="button"
                onClick={() => {
                  onSelect(file)
                  onClose()
                }}
                className="btn-secondary text-xs py-1 px-2.5"
              >
                Load into editor
              </button>
            )}
            <button
              type="button"
              onClick={onClose}
              aria-label="Close preview"
              className="text-zinc-400 hover:text-zinc-700 dark:hover:text-zinc-200 p-1 rounded-lg hover:bg-zinc-200 dark:hover:bg-zinc-800 transition cursor-pointer"
            >
              <CloseIcon className="w-4 h-4" />
            </button>
          </div>
        </div>
        <div className="p-4 overflow-auto flex-1 bg-zinc-950 text-zinc-100 font-mono text-xs leading-6 selection:bg-primary-500/30">
          <pre className="whitespace-pre-wrap wrap-break-word">
            {file.content || '(Empty file)'}
          </pre>
        </div>
      </div>
    </div>
  )
}
