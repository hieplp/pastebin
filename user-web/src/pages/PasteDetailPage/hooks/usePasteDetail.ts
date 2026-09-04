import { useState, useRef, useCallback, type RefObject } from 'react'
import type { PasteItem } from '@/api/types'
import { usePaste } from '@/hooks/usePastes'
import { useClipboard } from '@/hooks/useClipboard'
import { useLocation } from '@/lib/router'

export interface UsePasteDetailOptions {
  pasteId: string
}

export interface UsePasteDetailReturn {
  paste: PasteItem | null
  isLoading: boolean
  error: Error | null
  showCreatedAlert: boolean
  dismissCreatedAlert: () => void
  showRaw: boolean
  toggleRaw: () => void
  shareUrl: string
  urlInputRef: RefObject<HTMLInputElement | null>
  copiedUrl: boolean
  copiedContent: boolean
  lines: string[]
  handleCopyUrl: () => Promise<void>
  handleCopyContent: () => Promise<void>
}

// ponytail: paste detail controller managing paste fetching, sharing URLs, and clipboard actions
export function usePasteDetail({ pasteId }: UsePasteDetailOptions): UsePasteDetailReturn {
  const { data: paste, isLoading, error } = usePaste(pasteId)
  const { search } = useLocation()

  const [showCreatedAlert, setShowCreatedAlert] = useState(() => {
    return new URLSearchParams(search).get('created') === 'true'
  })
  const [showRaw, setShowRaw] = useState(false)

  const { copy: copyUrl, copied: copiedUrl } = useClipboard({ timeout: 2500 })
  const { copy: copyContent, copied: copiedContent } = useClipboard({ timeout: 2000 })
  const urlInputRef = useRef<HTMLInputElement>(null)

  const shareUrl =
    typeof window !== 'undefined'
      ? `${window.location.origin}/pastes/${paste?.alias || paste?.pasteId || pasteId}`
      : `/pastes/${pasteId}`

  const lines = (paste?.content ?? '').split('\n')

  const dismissCreatedAlert = useCallback(() => {
    setShowCreatedAlert(false)
  }, [])

  const toggleRaw = useCallback(() => {
    setShowRaw((prev) => !prev)
  }, [])

  const handleCopyUrl = useCallback(async () => {
    if (urlInputRef.current) {
      urlInputRef.current.focus()
      urlInputRef.current.select()
    }
    await copyUrl(shareUrl)
  }, [copyUrl, shareUrl])

  const handleCopyContent = useCallback(async () => {
    if (paste?.content) {
      await copyContent(paste.content)
    }
  }, [copyContent, paste])

  return {
    paste,
    isLoading,
    error,
    showCreatedAlert,
    dismissCreatedAlert,
    showRaw,
    toggleRaw,
    shareUrl,
    urlInputRef,
    copiedUrl,
    copiedContent,
    lines,
    handleCopyUrl,
    handleCopyContent,
  }
}
