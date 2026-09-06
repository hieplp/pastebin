import type { AsyncState } from '@/types'

export const INITIAL_ASYNC_STATE: AsyncState = {
  isLoading: false,
  error: null,
}

export function createAsyncState<TState extends object>(
  state: TState,
): TState & AsyncState {
  return { ...INITIAL_ASYNC_STATE, ...state }
}

export function createAsyncRunner<TState extends AsyncState>(
  set: (patch: Partial<TState>) => void,
) {
  return async <T>(action: () => Promise<T>): Promise<T> => {
    set({ isLoading: true, error: null } as Partial<TState>)
    try {
      const res = await action()
      set({ isLoading: false } as Partial<TState>)
      return res
    } catch (err) {
      const error = err instanceof Error ? err : new Error(String(err))
      set({ error, isLoading: false } as Partial<TState>)
      throw error
    }
  }
}
