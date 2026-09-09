import { create } from "zustand"

type AuthState = {
  username: string | null
  setUsername: (username: string | null) => void
}

// ponytail: in-memory username; JWT lives in httpOnly cookies
export const useAuthStore = create<AuthState>((set) => ({
  username: null,
  setUsername: (username) => set({ username }),
}))
