import { useState, useCallback, type DragEvent } from 'react'
import { EXTENSION_MAP } from '@/constants'
import type { UploadedFile } from '../components'

export interface ProcessedUpload {
  files: UploadedFile[]
  content: string
  title?: string
  syntax?: string
}

export interface UseFileUploadOptions {
  onUpload?: (upload: ProcessedUpload) => void
}

export interface DragProps {
  onDragOver: (e: DragEvent<HTMLElement>) => void
  onDragLeave: (e: DragEvent<HTMLElement>) => void
  onDrop: (e: DragEvent<HTMLElement>) => void
}

export interface UseFileUploadReturn {
  files: UploadedFile[]
  isDragging: boolean
  readFiles: (fileList: FileList | File[] | null) => Promise<ProcessedUpload | null>
  removeFile: (index: number) => void
  clearFiles: () => void
  dragProps: DragProps
}

// ponytail: native FileReader/file.text() file upload & drag-drop hook with zero dependencies
export function useFileUpload(options: UseFileUploadOptions = {}): UseFileUploadReturn {
  const { onUpload } = options
  const [files, setFiles] = useState<UploadedFile[]>([])
  const [isDragging, setIsDragging] = useState(false)

  const readFiles = useCallback(
    async (fileList: FileList | File[] | null): Promise<ProcessedUpload | null> => {
      if (!fileList || fileList.length === 0) return null

      const incoming = Array.from(fileList)
      const results = await Promise.all(
        incoming.map(async (f) => ({
          name: f.name,
          size: f.size,
          text: await f.text(),
        }))
      )

      let uploadResult: ProcessedUpload

      if (results.length === 1) {
        const file = results[0]
        const ext = file.name.split('.').pop()?.toLowerCase() ?? ''
        uploadResult = {
          files: [{ name: file.name, size: file.size, content: file.text }],
          title: file.name,
          content: file.text,
          syntax: EXTENSION_MAP[ext] || 'plaintext',
        }
      } else {
        const combined = results.map((f) => `// --- ${f.name} ---\n${f.text}`).join('\n\n')
        uploadResult = {
          files: results.map((r) => ({ name: r.name, size: r.size, content: r.text })),
          title: `Upload: ${results.length} files`,
          content: combined,
          syntax: 'plaintext',
        }
      }

      setFiles((prev) => [...prev, ...uploadResult.files])
      onUpload?.(uploadResult)
      return uploadResult
    },
    [onUpload]
  )

  const removeFile = useCallback((index: number) => {
    setFiles((prev) => prev.filter((_, i) => i !== index))
  }, [])

  const clearFiles = useCallback(() => {
    setFiles([])
  }, [])

  const onDragOver = useCallback((e: DragEvent<HTMLElement>) => {
    e.preventDefault()
    setIsDragging(true)
  }, [])

  const onDragLeave = useCallback((e: DragEvent<HTMLElement>) => {
    // Only reset if leaving target boundary
    if (!e.currentTarget.contains(e.relatedTarget as Node | null)) {
      setIsDragging(false)
    }
  }, [])

  const onDrop = useCallback(
    (e: DragEvent<HTMLElement>) => {
      e.preventDefault()
      setIsDragging(false)
      void readFiles(e.dataTransfer.files)
    },
    [readFiles]
  )

  return {
    files,
    isDragging,
    readFiles,
    removeFile,
    clearFiles,
    dragProps: {
      onDragOver,
      onDragLeave,
      onDrop,
    },
  }
}
