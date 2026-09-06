import { useEffect } from 'react'

export interface ShortcutOptions {
  /** Require Meta (Mac) or Ctrl (Windows/Linux) key */
  metaOrCtrl?: boolean
  /** Require Alt key */
  alt?: boolean
  /** Require Shift key */
  shift?: boolean
  /** Call e.preventDefault() on matching shortcut */
  preventDefault?: boolean
  /** Whether the shortcut listener is active */
  enabled?: boolean
}

// window/target keyboard shortcut listener with modifier support
export function useShortcut(
  key: string,
  handler: (e: KeyboardEvent) => void,
  options: ShortcutOptions = {},
) {
  const {
    metaOrCtrl = false,
    alt = false,
    shift = false,
    preventDefault = true,
    enabled = true,
  } = options

  useEffect(() => {
    if (!enabled) return

    const handleKeyDown = (e: KeyboardEvent) => {
      if (e.key.toLowerCase() !== key.toLowerCase()) return

      if (metaOrCtrl && !e.metaKey && !e.ctrlKey) return
      if (alt && !e.altKey) return
      if (shift && !e.shiftKey) return

      if (preventDefault) {
        e.preventDefault()
      }

      handler(e)
    }

    window.addEventListener('keydown', handleKeyDown)
    return () => window.removeEventListener('keydown', handleKeyDown)
  }, [key, handler, metaOrCtrl, alt, shift, preventDefault, enabled])
}
