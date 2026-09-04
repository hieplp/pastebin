import { SpinnerIcon } from '@/components/icons'

export interface PasteLoadingProps {
  message?: string
}

// ponytail: centered loading spinner indicator for paste detail loading state
export function PasteLoading({ message = 'Loading paste...' }: PasteLoadingProps) {
  return (
    <div className="flex-1 flex flex-col items-center justify-center min-h-[300px] gap-3 text-zinc-500">
      <SpinnerIcon className="w-8 h-8 animate-spin text-primary-500" />
      <p className="text-sm font-medium">{message}</p>
    </div>
  )
}
