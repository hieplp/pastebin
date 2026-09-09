import { createFileRoute, redirect } from "@tanstack/react-router"

import { HomePage } from "@/pages/home"
import { useAuthStore } from "@/stores/auth"

export const Route = createFileRoute("/")({
  beforeLoad: () => {
    if (!useAuthStore.getState().username) {
      throw redirect({ to: "/login" })
    }
  },
  component: HomePage,
})
