import { create } from 'zustand'
import { persist } from 'zustand/middleware'
import type { AsyncState, CreatePasteRequest, DraftPaste, Paste } from '@/types'
import { createAsyncRunner, createAsyncState } from '@/utils'
import { pasteApi } from '@/api'

/* ---- States ---- */

interface PasteState extends AsyncState {
  draft: DraftPaste
  currentPaste: Paste | null
}

export const DEFAULT_DRAFT: DraftPaste = {
  title: '',
  content: '',
  syntax: 'plaintext',
  expiration: 'never',
}

const INITIAL_STATE: PasteState = createAsyncState({
  draft: DEFAULT_DRAFT,
  currentPaste: null,
})

/* ---- Actions ---- */

interface PasteActions {
  // Draft
  setDraft: (
    updater: Partial<DraftPaste> | ((prev: DraftPaste) => DraftPaste),
  ) => void
  clearDraft: () => void
  // Pastes
  createPaste: (paste: CreatePasteRequest) => Promise<Paste>
  fetchPaste: (pasteId: string | number) => Promise<Paste>
  deletePaste: (pasteId: string | number) => Promise<void>
  clearCurrentPaste: () => void
}

/* ---- Stores ---- */

export type PasteStore = PasteState & PasteActions

export const usePasteStore = create<PasteStore>()(
  persist(
    (set) => {
      const run = createAsyncRunner(set)
      return {
        // State
        ...INITIAL_STATE,

        // Draft
        setDraft: (updater) => {
          set((state) => ({
            draft:
              typeof updater === 'function'
                ? updater(state.draft)
                : { ...state.draft, ...updater },
          }))
        },

        clearDraft: () => set({ draft: INITIAL_STATE.draft }),

        // Paste
        createPaste: (paste) =>
          run(async () => {
            const created = await pasteApi.create(paste)
            set({ currentPaste: created })
            return created
          }),

        fetchPaste: (pasteId) =>
          run(async () => {
            const paste = await pasteApi.getById(pasteId)
            set({ currentPaste: paste })
            return paste
          }),

        deletePaste: (pasteId) =>
          run(async () => {
            await pasteApi.delete(pasteId)
            set({ currentPaste: null })
          }),

        clearCurrentPaste: () => set({ currentPaste: null }),
      }
    },
    {
      name: 'paste',
      partialize: (state) => ({ draft: state.draft }),
    },
  ),
)
