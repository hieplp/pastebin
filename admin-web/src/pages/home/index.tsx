import { useNavigate } from "@tanstack/react-router"

import { Button } from "@/components/ui/button"
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card"
import { useAuthStore } from "@/stores/auth"

export function HomePage() {
  const navigate = useNavigate()
  const username = useAuthStore((s) => s.username)
  const setUsername = useAuthStore((s) => s.setUsername)

  return (
    <div className="flex min-h-svh items-center justify-center p-6">
      <Card className="w-full max-w-sm">
        <CardHeader>
          <CardTitle>Dashboard</CardTitle>
          <CardDescription>{username}</CardDescription>
        </CardHeader>
        <CardContent>
          <Button
            variant="outline"
            onClick={() => {
              setUsername(null)
              void navigate({ to: "/login" })
            }}
          >
            Sign out
          </Button>
        </CardContent>
      </Card>
    </div>
  )
}
