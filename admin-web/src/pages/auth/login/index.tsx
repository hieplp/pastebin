import { AuthShell } from "@/components/layout/auth-shell"
import { LoginForm } from "./login-form"

export function LoginPage() {
  return (
    <AuthShell>
      <LoginForm />
    </AuthShell>
  )
}
