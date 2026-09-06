import { navigate } from '@/lib/router'

export function DetailActions() {
  /* ---- Render ---- */
  return (
    <div className="flex items-center justify-between pt-1 pb-10">
      <button type="button" onClick={() => navigate('/')} className="btn-ghost">
        ← Create new paste
      </button>
    </div>
  )
}
