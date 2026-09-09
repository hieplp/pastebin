import { createFileRoute, redirect } from "@tanstack/react-router"

import { rootExistsQueryOptions } from "@/api/root"
import { LoginPage } from "@/pages/auth/login"
import { useAuthStore } from "@/stores/auth"

export const Route = createFileRoute("/(auth)/login")({
  beforeLoad: async ({ context }) => {
    if (useAuthStore.getState().username) {
      throw redirect({ to: "/" })
    }
    if (!(await context.queryClient.ensureQueryData(rootExistsQueryOptions))) {
      throw redirect({ to: "/setup" })
    }
  },
  component: LoginPage,
})
