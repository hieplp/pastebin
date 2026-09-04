import { UploadIcon } from '@/components/icons'

export interface DragOverlayProps {
  visible?: boolean
}

// ponytail: visual backdrop overlay when files are dragged over editor
export function DragOverlay({ visible = true }: DragOverlayProps) {
  if (!visible) return null

  return (
    <div className="absolute inset-0 z-20 flex flex-col items-center justify-center bg-white/85 dark:bg-zinc-950/85 backdrop-blur-sm rounded-2xl pointer-events-none transition-all">
      <div className="h-12 w-12 rounded-2xl bg-primary-500/10 border border-primary-500/30 flex items-center justify-center text-primary mb-3 shadow-lg shadow-primary-500/10">
        <UploadIcon className="h-6 w-6" />
      </div>
      <span className="text-primary-300 font-semibold text-base">Drop files here to load content</span>
      <span className="text-zinc-400 text-xs mt-1">Accepts code, logs, and plain text files</span>
    </div>
  )
}
