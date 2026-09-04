import { useEffect, useCallback } from 'react'
import { usePasteStore } from '../store'
import type { CreatePasteDto, PasteItem, PaginatedResponse } from '../api/types'

export interface UseCreatePasteReturn {
  createPaste: (dto: CreatePasteDto) => Promise<PasteItem>
  isPending: boolean
  error: Error | null
  data: PasteItem | null
  reset: () => void
}

// ponytail: lightweight mutation hook delegating to centralized Zustand paste store
export function useCreatePaste(): UseCreatePasteReturn {
  const createPaste = usePasteStore((s) => s.createPaste)
  const isPending = usePasteStore((s) => s.isLoading)
  const error = usePasteStore((s) => s.error)
  const data = usePasteStore((s) => s.currentPaste)
  const resetDraft = usePasteStore((s) => s.resetDraft)

  return {
    createPaste: (dto: CreatePasteDto) => createPaste(dto),
    isPending,
    error,
    data,
    reset: resetDraft,
  }
}

export interface UsePastesOptions {
  page?: number
  size?: number
  enabled?: boolean
}

export interface UsePastesReturn {
  data: PaginatedResponse<PasteItem> | null
  isLoading: boolean
  error: Error | null
  refetch: () => Promise<void>
}

// ponytail: query hook backed by Zustand store for fetching user pastes with pagination
export function usePastes(options: UsePastesOptions = {}): UsePastesReturn {
  const { page = 1, size = 10, enabled = true } = options
  const pastes = usePasteStore((s) => s.pastes)
  const isLoading = usePasteStore((s) => s.isLoading)
  const error = usePasteStore((s) => s.error)
  const fetchPastes = usePasteStore((s) => s.fetchPastes)

  const refetch = useCallback(async () => {
    await fetchPastes({ page, size })
  }, [fetchPastes, page, size])

  useEffect(() => {
    if (enabled) {
      void fetchPastes({ page, size })
    }
  }, [enabled, fetchPastes, page, size])

  return {
    data: pastes.length > 0 ? { items: pastes, total: pastes.length, page, pageSize: size } : null,
    isLoading,
    error,
    refetch,
  }
}

export interface UsePasteReturn {
  data: PasteItem | null
  isLoading: boolean
  error: Error | null
  refetch: () => Promise<void>
}

// ponytail: query hook backed by Zustand store for fetching a single paste by id
export function usePaste(pasteId?: string | number): UsePasteReturn {
  const currentPaste = usePasteStore((s) => s.currentPaste)
  const isLoading = usePasteStore((s) => s.isLoading)
  const error = usePasteStore((s) => s.error)
  const fetchPaste = usePasteStore((s) => s.fetchPaste)

  const refetch = useCallback(async () => {
    if (pasteId) await fetchPaste(pasteId)
  }, [pasteId, fetchPaste])

  useEffect(() => {
    if (pasteId) {
      void fetchPaste(pasteId)
    }
  }, [pasteId, fetchPaste])

  return {
    data:
      currentPaste &&
      (String(currentPaste.pasteId) === String(pasteId) ||
        (currentPaste.alias && String(currentPaste.alias) === String(pasteId)))
        ? currentPaste
        : null,
    isLoading,
    error,
    refetch,
  }
}
