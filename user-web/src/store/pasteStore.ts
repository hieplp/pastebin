import { create } from 'zustand'
import { persist } from 'zustand/middleware'
import { pasteApi } from '../api/pastes'
import type { CreatePasteDto, PasteItem } from '../api/types'
import { createAsyncRunner, type AsyncState, INITIAL_ASYNC_STATE } from './utils'

export interface PasteDraft {
  title: string
  content: string
  syntax: string
  expiration: string
}

export const DEFAULT_DRAFT: PasteDraft = {
  title: '',
  content: '',
  syntax: 'plaintext',
  expiration: 'never',
}
export interface PasteState extends AsyncState {
  draft: PasteDraft
  pastes: PasteItem[]
  currentPaste: PasteItem | null
}

export const INITIAL_STATE: PasteState = {
  ...INITIAL_ASYNC_STATE,
  draft: DEFAULT_DRAFT,
  pastes: [],
  currentPaste: null,
}
export const INITIAL_PASTE_STATE = INITIAL_STATE

export interface PasteActions {
  setDraft: (updater: Partial<PasteDraft> | ((prev: PasteDraft) => PasteDraft)) => void
  resetDraft: () => void
  fetchPastes: (params?: { page?: number; size?: number }) => Promise<PasteItem[]>
  fetchPaste: (id: string | number) => Promise<PasteItem>
  createPaste: (dto?: Partial<CreatePasteDto>) => Promise<PasteItem>
}

export type PasteStore = PasteState & PasteActions

// ponytail: centralized paste store backed by Zustand with persisted draft in localStorage
export const usePasteStore = create<PasteStore>()(
  persist(
    (set, get) => {
      const run = createAsyncRunner(set)
      return {
        ...INITIAL_STATE,

        setDraft: (updater) =>
          set((state) => ({
            draft: typeof updater === 'function' ? updater(state.draft) : { ...state.draft, ...updater },
          })),

        resetDraft: () => set({ draft: INITIAL_STATE.draft }),

        fetchPastes: (params) =>
          run(async () => {
            const res = await pasteApi.getOwn(params)
            set({ pastes: res.items })
            return res.items
          }),

        fetchPaste: (id) =>
          run(async () => {
            const item = await pasteApi.getById(id)
            set({ currentPaste: item })
            return item
          }),

        createPaste: (dto) =>
          run(async () => {
            const draft = get().draft
            const created = await pasteApi.create({
              title: dto?.title ?? draft.title,
              content: dto?.content ?? draft.content,
              syntax: dto?.syntax ?? draft.syntax,
              expiredAt: dto?.expiredAt,
              privacy: dto?.privacy,
              alias: dto?.alias,
            })
            set((state) => ({
              pastes: [created, ...state.pastes],
              currentPaste: created,
            }))
            get().resetDraft()
            return created
          }),
      }
    },
    {
      name: 'pastebin_draft',
      partialize: (state) => ({ draft: state.draft }),
    }
  )
)
