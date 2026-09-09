import { zodResolver } from "@hookform/resolvers/zod"
import { useMutation } from "@tanstack/react-query"
import { useNavigate } from "@tanstack/react-router"
import { cn } from "cn"
import { useForm } from "react-hook-form"

import { loginRoot } from "@/api/root"
import { FormField } from "@/components/form/form-field"
import { LoadingButton } from "@/components/form/loading-button"
import { AuthBrand } from "@/components/layout/auth-shell"
import { Field, FieldGroup } from "@/components/ui/field"
import { toast } from "@/components/ui/toast"
import { useAuthStore } from "@/stores/auth"
import { loginSchema, type LoginValues } from "./schema"

export function LoginForm({
  className,
  ...props
}: React.ComponentProps<"div">) {
  const navigate = useNavigate()
  const setUsername = useAuthStore((s) => s.setUsername)
  const {
    register,
    handleSubmit,
    formState: { errors, isSubmitting },
  } = useForm<LoginValues>({
    resolver: zodResolver(loginSchema),
    defaultValues: { username: "", password: "" },
  })
  const mutation = useMutation({
    mutationFn: loginRoot,
    onSuccess: (data) => {
      setUsername(data.username)
      toast.add({ type: "success", title: "Signed in" })
      void navigate({ to: "/" })
    },
    onError: (err) => {
      toast.add({
        type: "error",
        title: err instanceof Error ? err.message : "Failed to sign in",
        priority: "high",
      })
    },
  })
  const pending = isSubmitting || mutation.isPending

  const onSubmit = handleSubmit(async (values) => {
    await mutation.mutateAsync(values)
  })

  return (
    <div className={cn("flex flex-col gap-6", className)} {...props}>
      <form onSubmit={onSubmit}>
        <FieldGroup>
          <AuthBrand title="Welcome back" />
          <FormField
            id="username"
            label="Username"
            placeholder="admin"
            autoComplete="username"
            error={errors.username}
            {...register("username")}
            disabled={pending}
          />
          <FormField
            id="password"
            label="Password"
            type="password"
            placeholder="••••••••"
            autoComplete="current-password"
            error={errors.password}
            {...register("password")}
            disabled={pending}
          />
          <Field>
            <LoadingButton type="submit" loading={pending}>
              Sign in
            </LoadingButton>
          </Field>
        </FieldGroup>
      </form>
    </div>
  )
}
