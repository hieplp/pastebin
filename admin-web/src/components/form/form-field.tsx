import { useState } from "react"
import { EyeIcon, EyeOffIcon } from "lucide-react"

import { Field, FieldError, FieldLabel } from "@/components/ui/field"
import { Input } from "@/components/ui/input"
import {
  InputGroup,
  InputGroupAddon,
  InputGroupButton,
  InputGroupInput,
} from "@/components/ui/input-group"

type FormFieldProps = React.ComponentProps<"input"> & {
  label: string
  error?: { message?: string }
}

export function FormField({
  id,
  label,
  error,
  disabled,
  type,
  ...props
}: FormFieldProps) {
  const invalid = !!error || undefined
  const [visible, setVisible] = useState(false)
  const isPassword = type === "password"

  return (
    <Field data-invalid={invalid} data-disabled={disabled || undefined}>
      <FieldLabel htmlFor={id}>{label}</FieldLabel>
      {isPassword ? (
        <InputGroup>
          <InputGroupInput
            id={id}
            aria-invalid={invalid}
            type={visible ? "text" : "password"}
            disabled={disabled}
            {...props}
          />
          <InputGroupAddon align="inline-end">
            <InputGroupButton
              size="icon-xs"
              disabled={disabled}
              aria-label={visible ? "Hide password" : "Show password"}
              aria-pressed={visible}
              onClick={() => setVisible((v) => !v)}
            >
              {visible ? <EyeOffIcon /> : <EyeIcon />}
            </InputGroupButton>
          </InputGroupAddon>
        </InputGroup>
      ) : (
        <Input
          id={id}
          aria-invalid={invalid}
          type={type}
          {...props}
          disabled={disabled}
        />
      )}
      <FieldError errors={[error]} />
    </Field>
  )
}
