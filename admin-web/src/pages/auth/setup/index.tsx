import { AuthShell } from "@/components/layout/auth-shell"
import { SetupForm } from "./setup-form"

export function SetupPage() {
  return (
    <AuthShell>
      <SetupForm />
    </AuthShell>
  )
}
