import { useState, useMemo, type ReactNode } from 'react'
import { createPortal } from 'react-dom'
import type { UploadedFile } from '@/types'
import { PreviewModal } from '@/components/ui/PreviewModal'
import { cn } from '@/utils'

/* ---- TabButton ---- */

interface TabButtonProps {
  label: string
  active: boolean
  onClick: () => void
  children?: ReactNode
}

function TabButton({ label, active, onClick, children }: TabButtonProps) {
  return (
    <button
      type="button"
      onClick={onClick}
      className={cn(
        'inline-flex items-center gap-1.5 px-2.5 py-1 rounded-lg text-xs font-mono transition shrink-0 cursor-pointer border',
        active
          ? 'bg-primary-500/10 border-primary-500/40 text-primary-800 dark:text-primary font-medium shadow-xs'
          : 'bg-white/80 dark:bg-zinc-900/80 border-zinc-200 dark:border-zinc-800/80 text-zinc-600 dark:text-zinc-400 hover:text-zinc-900 dark:hover:text-zinc-200 hover:border-zinc-300 dark:hover:border-zinc-700',
      )}
    >
      <span className="truncate max-w-44">{label}</span>
      {children}
    </button>
  )
}

/* ---- DetailFiles ---- */

interface DetailFilesProps {
  files: UploadedFile[]
  hasText: boolean
  textTitle?: string
  activeTab: string
  onSelectTab: (tab: string) => void
}

export function DetailFiles({
  files,
  hasText,
  textTitle,
  activeTab,
  onSelectTab,
}: DetailFilesProps) {
  /* ---- Hooks ---- */
  const [search, setSearch] = useState('')
  const [previewFile, setPreviewFile] = useState<UploadedFile | null>(null)

  const filteredFiles = useMemo(() => {
    if (!search.trim()) return files
    const query = search.toLowerCase()
    return files.filter((f) => f.name.toLowerCase().includes(query))
  }, [files, search])

  /* ---- Render ---- */
  if (files.length === 0) return null

  return (
    <>
      <div className="border-b border-zinc-200 dark:border-zinc-800/80 bg-zinc-100/80 dark:bg-zinc-950/60 px-3 sm:px-4 py-2 flex flex-wrap items-center justify-between gap-2.5 shrink-0">
        {/* Search input when many files */}
        {files.length > 5 && (
          <div className="flex items-center">
            <input
              type="text"
              placeholder="Filter files..."
              value={search}
              onChange={(e) => setSearch(e.target.value)}
              className="px-2 py-0.5 text-xs rounded-md bg-white dark:bg-zinc-900 border border-zinc-200 dark:border-zinc-800 text-zinc-900 dark:text-zinc-100 placeholder:text-zinc-400 focus:outline-none focus:border-primary-500 w-32 sm:w-44"
            />
          </div>
        )}

        {/* Scrollable File Tabs Bar */}
        <div className="w-full flex items-center gap-1.5 overflow-x-auto pt-1 pb-0.5 scrollbar-thin">
          {/* Main Text / Notes Tab (if paste has both text and files) */}
          {hasText && (
            <TabButton
              label={textTitle || 'Text / Notes'}
              active={activeTab === 'text'}
              onClick={() => onSelectTab('text')}
            />
          )}

          {/* Individual Attached Files */}
          {filteredFiles.map((file) => (
            <TabButton
              key={file.name}
              label={file.name}
              active={activeTab === file.name}
              onClick={() => onSelectTab(file.name)}
            >
              <span className="text-[10px] opacity-60">
                ({(file.size / 1024).toFixed(1)} KB)
              </span>
            </TabButton>
          ))}
          {filteredFiles.length === 0 && (
            <span className="text-xs text-zinc-400 italic py-0.5">
              No matching files
            </span>
          )}
        </div>
      </div>

      {/* Preview modal */}
      {previewFile &&
        createPortal(
          <PreviewModal
            file={previewFile}
            onClose={() => setPreviewFile(null)}
          />,
          document.body,
        )}
    </>
  )
}
