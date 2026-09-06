import { useState, useCallback, type DragEvent } from 'react'

export interface DragProps {
  onDragOver: (e: DragEvent<HTMLElement>) => void
  onDragLeave: (e: DragEvent<HTMLElement>) => void
  onDrop: (e: DragEvent<HTMLElement>) => void
}

export interface UseDragDropOptions {
  onDropFiles: (files: FileList) => void
}

export interface UseDragDropReturn {
  isDragging: boolean
  dragProps: DragProps
}

export function useDragDrop({
  onDropFiles,
}: UseDragDropOptions): UseDragDropReturn {
  const [isDragging, setIsDragging] = useState(false)

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
      onDropFiles(e.dataTransfer.files)
    },
    [onDropFiles],
  )

  return {
    isDragging,
    dragProps: {
      onDragOver,
      onDragLeave,
      onDrop,
    },
  }
}
