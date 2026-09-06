import { DocumentIcon, UploadIcon } from '@/components/icons'
import { EXPIRATION_OPTIONS, SYNTAX_OPTIONS } from '@/constants.ts'
import { Input, Select } from '@/components/ui'

interface EditorToolbarProps {
  title: string
  syntax: string
  expiration: string
  onChange: (field: string, value: any) => void
  onUploadClick: () => void
}

export function EditorToolbar({
  title,
  syntax,
  expiration,
  onChange,
  onUploadClick,
}: EditorToolbarProps) {
  /* ---- Render ---- */
  return (
    <div
      className={
        'border-b border-zinc-200 dark:border-zinc-800/80 bg-zinc-50/70 dark:bg-zinc-900/60 p-3 sm:px-4 flex flex-wrap items-center justify-between gap-3'
      }
    >
      {/* Title / Filename Input */}
      <div className="flex-1 min-w-55">
        <Input
          icon={<DocumentIcon className="h-4 w-4 text-zinc-500 shrink-0" />}
          placeholder="Paste title or filename (optional)"
          value={title}
          onChange={(e) => onChange('title', e.target.value)}
        />
      </div>

      {/* Selectors & Upload Button */}
      <div className="flex items-center gap-2 flex-wrap">
        <Select
          value={syntax}
          onChange={(e) => onChange('syntax', e.target.value)}
          options={SYNTAX_OPTIONS}
        />

        <Select
          value={expiration}
          onChange={(e) => onChange('expiration', e.target.value)}
          options={EXPIRATION_OPTIONS}
        />

        {/* File Upload Trigger */}
        <button
          type="button"
          onClick={onUploadClick}
          title="Upload code or text files"
          className="btn-secondary sm:text-sm"
        >
          <UploadIcon className="h-3.5 w-3.5 text-zinc-400" />
          <span>Upload</span>
        </button>
      </div>
    </div>
  )
}
