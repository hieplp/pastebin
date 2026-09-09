import { cn } from "cn"
import { GalleryVerticalEndIcon } from "lucide-react"

import { FieldDescription } from "@/components/ui/field"

export function AuthShell({
  className,
  ...props
}: React.ComponentProps<"div">) {
  return (
    <div className="flex min-h-svh flex-col items-center justify-center gap-6 bg-background p-6 md:p-10">
      <div className={cn("w-full max-w-sm", className)} {...props} />
    </div>
  )
}

export function AuthBrand({
  title,
  description,
}: {
  title: string
  description?: React.ReactNode
}) {
  return (
    <div className="flex flex-col items-center gap-2 text-center">
      <div className="flex flex-col items-center gap-2 font-medium">
        <div className="flex size-8 items-center justify-center rounded-md">
          <GalleryVerticalEndIcon className="size-6" />
        </div>
        <span className="sr-only">pastebin</span>
      </div>
      <h1 className="text-xl font-bold">{title}</h1>
      {description && <FieldDescription>{description}</FieldDescription>}
    </div>
  )
}
