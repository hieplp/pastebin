import { useState, useCallback, useRef, useEffect } from 'react'

export interface UseClipboardOptions {
  timeout?: number
}

export interface UseClipboardReturn {
  copy: (text: string) => Promise<boolean>
  copied: boolean
}

// ponytail: clipboard copy with navigator.clipboard and execCommand fallback for insecure/iframe contexts
export function copyFallback(text: string): boolean {
  if (typeof document === 'undefined') return false

  const textarea = document.createElement('textarea')
  textarea.value = text
  textarea.style.position = 'fixed'
  textarea.style.left = '-9999px'
  textarea.style.top = '0'
  textarea.style.opacity = '0'
  textarea.setAttribute('readonly', '')

  document.body.appendChild(textarea)
  textarea.focus()
  textarea.select()

  let ok = false
  try {
    ok = document.execCommand('copy')
  } catch {
    ok = false
  }

  document.body.removeChild(textarea)
  return ok
}

export async function copyToClipboard(text: string): Promise<boolean> {
  if (typeof navigator !== 'undefined' && navigator.clipboard?.writeText) {
    try {
      await navigator.clipboard.writeText(text)
      return true
    } catch {
      return copyFallback(text)
    }
  }
  return copyFallback(text)
}

export function useClipboard({
  timeout = 2000,
}: UseClipboardOptions = {}): UseClipboardReturn {
  const [copied, setCopied] = useState(false)
  const timerRef = useRef<number | null>(null)

  const copy = useCallback(
    async (text: string): Promise<boolean> => {
      clearTimeout(timerRef.current ?? undefined)

      const ok = await copyToClipboard(text)

      if (ok) {
        setCopied(true)
        timerRef.current = window.setTimeout(() => {
          setCopied(false)
          timerRef.current = null
        }, timeout)
        return true
      }

      setCopied(false)
      return false
    },
    [timeout],
  )

  useEffect(() => {
    return () => {
      clearTimeout(timerRef.current ?? undefined)
    }
  }, [])

  return { copy, copied }
}
