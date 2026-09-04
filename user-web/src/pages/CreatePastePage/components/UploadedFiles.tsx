import { useState, useEffect, useCallback } from 'react'
import { cn } from '@/lib/utils'
import { CloseIcon } from '@/components/icons'

export interface UploadedFile {
  name: string
  size: number
  content?: string
}

export interface UploadedFilesProps {
  files: UploadedFile[]
  onClear: () => void
  onRemove?: (index: number) => void
  onSelectFile?: (file: UploadedFile) => void
  className?: string
}

/* ---- Inner components ---- */

function FileBadge({
  file,
  onPreview,
  onRemove,
}: {
  file: UploadedFile
  onPreview: () => void
  onRemove?: () => void
}) {
  return (
    <span className="badge-secondary">
      <button
        type="button"
        onClick={onPreview}
        title={`Click to view ${file.name}`}
        className="truncate max-w-40 hover:underline hover:text-zinc-900 dark:hover:text-zinc-100 cursor-pointer text-left"
      >
        {file.name}
      </button>
      <span className="text-zinc-500">({(file.size / 1024).toFixed(1)} KB)</span>
      {onRemove && (
        <button
          type="button"
          onClick={onRemove}
          aria-label={`Remove ${file.name}`}
          className="text-zinc-400 hover:text-zinc-700 dark:hover:text-zinc-200 -mr-1 p-0.5 rounded hover:bg-zinc-200 dark:hover:bg-zinc-800 transition cursor-pointer inline-flex items-center justify-center"
        >
          <CloseIcon className="w-3 h-3" />
        </button>
      )}
    </span>
  )
}

function PreviewModal({
  file,
  onSelect,
  onClose,
}: {
  file: UploadedFile
  onSelect?: (file: UploadedFile) => void
  onClose: () => void
}) {
  useEffect(() => {
    const handleKeyDown = (e: globalThis.KeyboardEvent) => {
      if (e.key === 'Escape') onClose()
    }
    window.addEventListener('keydown', handleKeyDown)
    return () => window.removeEventListener('keydown', handleKeyDown)
  }, [onClose])

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
          <pre className="whitespace-pre-wrap wrap-break-word">{file.content || '(Empty file)'}</pre>
        </div>
      </div>
    </div>
  )
}

/* ---- Main component ---- */

export function UploadedFiles({
  files,
  onClear,
  onRemove,
  onSelectFile,
  className,
}: UploadedFilesProps) {
  const [previewFile, setPreviewFile] = useState<UploadedFile | null>(null)

  const closePreview = useCallback(() => setPreviewFile(null), [])

  const handleRemove = useCallback(
    (idx: number) => {
      if (previewFile === files[idx]) setPreviewFile(null)
      onRemove?.(idx)
    },
    [previewFile, files, onRemove],
  )

  if (files.length === 0) return null
  return (
    <>
      <div
        className={cn('border-b border-zinc-200 dark:border-zinc-800/60 bg-zinc-100/70 dark:bg-zinc-950/40 px-4 py-2 flex flex-wrap items-center gap-2 text-xs', className)}
      >
        <span className="text-zinc-500 text-[11px] uppercase tracking-wider font-semibold">Loaded files:</span>
        {files.map((file, idx) => (
          <FileBadge
            key={`${file.name}-${idx}`}
            file={file}
            onPreview={() => setPreviewFile(file)}
            onRemove={onRemove ? () => handleRemove(idx) : undefined}
          />
        ))}
        <button
          type="button"
          onClick={onClear}
          className="text-zinc-400 hover:text-zinc-200 text-xs underline cursor-pointer ml-auto"
        >
          Clear files
        </button>
      </div>

      {previewFile && (
        <PreviewModal file={previewFile} onSelect={onSelectFile} onClose={closePreview} />
      )}
    </>
  )
}
