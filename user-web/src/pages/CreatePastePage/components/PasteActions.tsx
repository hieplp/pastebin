import { LoadingButton } from '@/components/ui'

interface PasteAction {
  onClick: () => void
  loading?: boolean
  enabled: boolean
}

interface PasteActionsProps {
  clear: PasteAction
  submit: PasteAction
}

export function PasteActions({ clear, submit }: PasteActionsProps) {
  /* ---- Render ---- */
  return (
    <div className={'flex items-center justify-between pt-1 pb-10'}>
      <button
        type="button"
        onClick={clear.onClick}
        disabled={!clear.enabled || submit.loading}
        className="btn-ghost"
      >
        Reset draft
      </button>

      <div className="flex items-center gap-3">
        <LoadingButton
          onClick={submit.onClick}
          disabled={!submit.enabled}
          loading={submit.loading}
        />
      </div>
    </div>
  )
}
