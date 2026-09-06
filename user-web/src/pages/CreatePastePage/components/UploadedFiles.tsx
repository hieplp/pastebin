import type { UploadedFile } from '@/types'
import { CloseIcon } from '@/components/icons'
import { useCallback, useState } from 'react'
import { createPortal } from 'react-dom'
import { PreviewModal } from '@/components/ui'

/* ---- FileBadge ---- */

interface FileBadgeProps {
  file: UploadedFile
  onPreview: () => void
  onRemove: () => void
}

function FileBadge({ file, onPreview, onRemove }: FileBadgeProps) {
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
      <span className="text-zinc-500">
        ({(file.size / 1024).toFixed(1)} KB)
      </span>
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

/* ---- UploadedFiles ---- */

interface UploadedFilesProps {
  files: UploadedFile[]
  onClear: () => void
  onRemove: (index: number) => void
  onSelect?: (file: UploadedFile) => void
}

export function UploadedFiles({
  files,
  onClear,
  onRemove,
  onSelect,
}: UploadedFilesProps) {
  /* ---- Hooks ---- */
  const [previewFile, setPreviewFile] = useState<UploadedFile | null>(null)

  /* ---- Functions ---- */
  const handleRemove = useCallback(
    (idx: number) => {
      if (previewFile === files[idx]) setPreviewFile(null)
      onRemove(idx)
    },
    [previewFile, files],
  )

  if (files.length === 0) {
    return null
  }

  return (
    <>
      <div
        className={
          'border-b border-zinc-200 dark:border-zinc-800/60 bg-zinc-100/70 dark:bg-zinc-950/40 px-4 py-2 flex flex-wrap items-center gap-2 text-xs'
        }
      >
        <span className="text-zinc-500 text-[11px] uppercase tracking-wider font-semibold">
          Loaded files:
        </span>
        {files.map((file, idx) => (
          <FileBadge
            key={`${file.name}-${idx}`}
            file={file}
            onPreview={() => setPreviewFile(file)}
            onRemove={() => handleRemove(idx)}
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

      {previewFile &&
        createPortal(
          <PreviewModal
            file={previewFile}
            onSelect={onSelect}
            onClose={() => setPreviewFile(null)}
          />,
          document.body,
        )}
    </>
  )
}
