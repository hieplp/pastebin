import { cn } from '@/utils'
import { DownloadIcon } from '@/components/icons'
import { SYNTAX_TO_EXT } from '@/constants.ts'
import type { ButtonHTMLAttributes } from 'react'

export interface DownloadButtonProps extends ButtonHTMLAttributes<HTMLButtonElement> {
  content: string
  title: string
  syntax: string
}

// download button for exporting paste as file
export function DownloadButton({
  content,
  title,
  syntax,
  className = '',
  ...props
}: DownloadButtonProps) {
  const handleDownload = () => {
    const blob = new Blob([content], {
      type: 'text/plain;charset=utf-8',
    })
    const url = URL.createObjectURL(blob)
    const a = document.createElement('a')
    a.href = url

    const ext = SYNTAX_TO_EXT[syntax] || 'txt'
    const hasExt = title.includes('.')
    const filename = hasExt ? title : `${title}.${ext}`

    a.download = filename
    document.body.appendChild(a)
    a.click()
    document.body.removeChild(a)
    URL.revokeObjectURL(url)
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
