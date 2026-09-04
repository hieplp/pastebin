import { cn } from '@/lib/utils'
import { Input, Select } from '@/components/ui'
import { DocumentIcon, UploadIcon } from '@/components/icons'
import { SYNTAX_OPTIONS, EXPIRATION_OPTIONS } from '@/constants'

export interface PasteToolbarProps {
  title: string
  syntax: string
  expiration: string
  onTitleChange: (title: string) => void
  onSyntaxChange: (syntax: string) => void
  onExpirationChange: (expiration: string) => void
  onUploadClick: () => void
  className?: string
}

// ponytail: paste configuration toolbar with title, syntax, expiration, and file upload trigger
export function PasteToolbar({
  title,
  syntax,
  expiration,
  onTitleChange,
  onSyntaxChange,
  onExpirationChange,
  onUploadClick,
  className,
}: PasteToolbarProps) {
  return (
    <div
      className={cn('border-b border-zinc-200 dark:border-zinc-800/80 bg-zinc-50/70 dark:bg-zinc-900/60 p-3 sm:px-4 flex flex-wrap items-center justify-between gap-3', className)}
    >
      {/* Title / Filename Input */}
      <div className="flex-1 min-w-55">
        <Input
          icon={<DocumentIcon className="h-4 w-4 text-zinc-500 shrink-0" />}
          placeholder="Paste title or filename (optional)"
          value={title}
          onChange={(e) => onTitleChange(e.target.value)}
        />
      </div>

      {/* Selectors & Upload Button */}
      <div className="flex items-center gap-2 flex-wrap">
        <Select
          value={syntax}
          onChange={(e) => onSyntaxChange(e.target.value)}
          options={SYNTAX_OPTIONS}
        />

        <Select
          value={expiration}
          onChange={(e) => onExpirationChange(e.target.value)}
          options={EXPIRATION_OPTIONS}
        />

        {/* File Upload Trigger */}
        <button
          type="button"
          onClick={onUploadClick}
          title="Upload code or text files"
          className="btn-secondary"
        >
          <UploadIcon className="h-3.5 w-3.5 text-zinc-400" />
          <span>Upload</span>
        </button>
      </div>
    </div>
  )
}
