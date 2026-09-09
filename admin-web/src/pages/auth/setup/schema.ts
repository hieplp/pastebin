import { z } from "zod"

export const setupSchema = z
  .object({
    token: z.string().min(1, "Token is required"),
    username: z
      .string()
      .min(3, "Username must be 3-50 characters")
      .max(50, "Username must be 3-50 characters")
      .regex(
        /^[a-zA-Z0-9_-]+$/,
        "Username must be alphanumeric, hyphens or underscores"
      ),
    password: z.string().min(8, "Password must be at least 8 characters"),
    confirmPassword: z.string().min(1, "Confirm password is required"),
  })
  .refine((data) => data.password === data.confirmPassword, {
    message: "Passwords do not match",
    path: ["confirmPassword"],
  })

export type SetupValues = z.infer<typeof setupSchema>
