import { cn } from '@/lib/utils'
import { LoadingButton } from '@/components/ui'

export interface PasteActionsProps {
  canClear: boolean
  canSubmit: boolean
  submitting: boolean
  onClear: () => void
  onSubmit: () => void
  className?: string
}

// ponytail: primary draft reset and paste creation button group
export function PasteActions({
  canClear,
  canSubmit,
  submitting,
  onClear,
  onSubmit,
  className,
}: PasteActionsProps) {
  return (
    <div className={cn('flex items-center justify-between pt-1 pb-10', className)}>
      <button
        type="button"
        onClick={onClear}
        disabled={!canClear}
        className="btn-ghost"
      >
        Reset draft
      </button>

      <div className="flex items-center gap-3">
        <LoadingButton
          onSubmit={onSubmit}
          canSubmit={canSubmit}
          submitting={submitting}
        />
      </div>
    </div>
  )
}
