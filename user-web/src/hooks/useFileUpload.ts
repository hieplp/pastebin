import { useState, useCallback } from 'react'
import type { ProcessedUpload, UploadedFile } from '@/types'
import { readFilesToUpload } from '@/utils'

export interface UseFileUploadOptions {
  onUpload?: (upload: ProcessedUpload) => void
}

export interface UseFileUploadReturn {
  files: UploadedFile[]
  readFiles: (
    fileList: FileList | File[] | null,
  ) => Promise<ProcessedUpload | null>
  removeFile: (index: number) => void
  clearFiles: () => void
}

// ponytail: native file.text() file upload hook with zero dependencies
export function useFileUpload({
  onUpload,
}: UseFileUploadOptions = {}): UseFileUploadReturn {
  const [files, setFiles] = useState<UploadedFile[]>([])

  const readFiles = useCallback(
    async (fileList: FileList | File[] | null) => {
      const uploadResult = await readFilesToUpload(fileList)
      if (!uploadResult) return null

      setFiles((prev) => [...prev, ...uploadResult.files])
      onUpload?.(uploadResult)
      return uploadResult
    },
    [onUpload],
  )

  const removeFile = useCallback((index: number) => {
    setFiles((prev) => prev.filter((_, i) => i !== index))
  }, [])

  const clearFiles = useCallback(() => {
    setFiles([])
  }, [])

  return {
    files,
    readFiles,
    removeFile,
    clearFiles,
  }
}
