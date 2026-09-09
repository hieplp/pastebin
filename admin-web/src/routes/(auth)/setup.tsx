import { createFileRoute, redirect } from "@tanstack/react-router"

import { rootExistsQueryOptions } from "@/api/root"
import { SetupPage } from "@/pages/auth/setup"

export const Route = createFileRoute("/(auth)/setup")({
  beforeLoad: async ({ context }) => {
    if (await context.queryClient.ensureQueryData(rootExistsQueryOptions)) {
      throw redirect({ to: "/login" })
    }
  },
  component: SetupPage,
})
