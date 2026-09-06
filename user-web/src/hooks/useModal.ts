import { useEffect } from 'react'

export interface UseModalOptions {
  // whether to lock body scrolling while modal is open
  lockScroll?: boolean
  // whether the modal listeners are active
  enabled?: boolean
}

// modal dismiss lifecycle: escape key close + body scroll lock
// ponytail: global body overflow lock, per-modal ref-count if stacked modals matter
export function useModal(onClose: () => void, options: UseModalOptions = {}) {
  const { lockScroll = true, enabled = true } = options

  useEffect(() => {
    if (!enabled) return

    const handleKeyDown = (e: KeyboardEvent) => {
      if (e.key === 'Escape') {
        onClose()
      }
    }

    window.addEventListener('keydown', handleKeyDown)

    let prevOverflow: string | undefined
    if (lockScroll && typeof document !== 'undefined') {
      prevOverflow = document.body.style.overflow
      document.body.style.overflow = 'hidden'
    }

    return () => {
      window.removeEventListener('keydown', handleKeyDown)
      if (
        lockScroll &&
        typeof document !== 'undefined' &&
        prevOverflow !== undefined
      ) {
        document.body.style.overflow = prevOverflow
      }
    }
  }, [onClose, lockScroll, enabled])
}
