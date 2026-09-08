import { cn } from '@/utils'
import { DownloadIcon } from '@/components/icons'
import { SYNTAX_TO_EXT } from '@/constants.ts'
import { fileApi } from '@/api'
import type { ButtonHTMLAttributes } from 'react'

export interface DownloadButtonProps extends ButtonHTMLAttributes<HTMLButtonElement> {
  content: string
  title: string
  syntax: string
  fileId?: string
}

// download button for exporting paste as file
export function DownloadButton({
  content,
  title,
  syntax,
  fileId,
  className = '',
  ...props
}: DownloadButtonProps) {
  const handleDownload = () => {
    const a = document.createElement('a')
    let objectUrl: string | undefined

    if (fileId) {
      a.href = fileApi.downloadUrl(fileId)
      a.download = title
    } else {
      objectUrl = URL.createObjectURL(
        new Blob([content], { type: 'text/plain;charset=utf-8' }),
      )
      a.href = objectUrl
      const ext = SYNTAX_TO_EXT[syntax] || 'txt'
      a.download = title.includes('.') ? title : `${title}.${ext}`
    }

    document.body.appendChild(a)
    a.click()
    document.body.removeChild(a)
    if (objectUrl) URL.revokeObjectURL(objectUrl)
  }

  return (
    <button
      type="button"
      title="Download paste file"
      className={cn('btn-secondary', className)}
      onClick={handleDownload}
      {...props}
    >
      <DownloadIcon className="h-3.5 w-3.5 text-zinc-400" />
      <span>Download</span>
    </button>
  )
}
