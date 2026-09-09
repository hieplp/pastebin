import { zodResolver } from "@hookform/resolvers/zod"
import { useMutation, useQueryClient } from "@tanstack/react-query"
import { useNavigate } from "@tanstack/react-router"
import { cn } from "cn"
import { useForm } from "react-hook-form"

import { createRoot, rootExistsQueryOptions } from "@/api/root"
import { FormField } from "@/components/form/form-field"
import { LoadingButton } from "@/components/form/loading-button"
import { AuthBrand } from "@/components/layout/auth-shell"
import { Field, FieldError, FieldGroup } from "@/components/ui/field"
import { toast } from "@/components/ui/toast"
import { setupSchema, type SetupValues } from "./schema"

export function SetupForm({
  className,
  ...props
}: React.ComponentProps<"div">) {
  const navigate = useNavigate()
  const queryClient = useQueryClient()
  const {
    register,
    handleSubmit,
    formState: { errors, isSubmitting },
  } = useForm<SetupValues>({
    resolver: zodResolver(setupSchema),
    defaultValues: {
      token: "",
      username: "",
      password: "",
      confirmPassword: "",
    },
  })
  const mutation = useMutation({
    mutationFn: createRoot,
    onSuccess: () => {
      queryClient.setQueryData(rootExistsQueryOptions.queryKey, true)
      toast.add({ type: "success", title: "Root created" })
      void navigate({ to: "/login" })
    },
    onError: (err) => {
      toast.add({
        type: "error",
        title: err instanceof Error ? err.message : "Failed to create root",
        priority: "high",
      })
    },
  })
  const pending = isSubmitting || mutation.isPending

  const onSubmit = handleSubmit(async ({ token, username, password }) => {
    await mutation.mutateAsync({ token, username, password })
  })

  return (
    <div className={cn("flex flex-col gap-6", className)} {...props}>
      <form onSubmit={onSubmit}>
        <FieldGroup>
          <AuthBrand title="Create root" />
          <FormField
            id="token"
            label="Root token"
            placeholder="Init token"
            autoComplete="off"
            error={errors.token}
            disabled={pending}
            {...register("token")}
          />
          <FormField
            id="username"
            label="Username"
            placeholder="admin"
            autoComplete="username"
            error={errors.username}
            disabled={pending}
            {...register("username")}
          />
          <FormField
            id="password"
            label="Password"
            type="password"
            placeholder="••••••••"
            autoComplete="new-password"
            error={errors.password}
            disabled={pending}
            {...register("password")}
          />
          <FormField
            id="confirmPassword"
            label="Confirm password"
            type="password"
            placeholder="••••••••"
            autoComplete="new-password"
            error={errors.confirmPassword}
            disabled={pending}
            {...register("confirmPassword")}
          />
          {mutation.error ? (
            <FieldError>{mutation.error.message}</FieldError>
          ) : null}
          <Field>
            <LoadingButton type="submit" loading={pending}>
              Create root
            </LoadingButton>
          </Field>
        </FieldGroup>
      </form>
    </div>
  )
}
