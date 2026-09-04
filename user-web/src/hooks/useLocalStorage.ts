import { useState, useEffect, useCallback } from 'react'

// ponytail: native localStorage hook with cross-tab sync and storage quota safety
export function useLocalStorage<T>(
  key: string,
  initialValue: T | (() => T)
): [T, (value: T | ((prev: T) => T)) => void, () => void] {
  const readValue = useCallback((): T => {
    if (typeof window === 'undefined') {
      return initialValue instanceof Function ? initialValue() : initialValue
    }

    try {
      const item = window.localStorage.getItem(key)
      return item ? (JSON.parse(item) as T) : initialValue instanceof Function ? initialValue() : initialValue
    } catch {
      return initialValue instanceof Function ? initialValue() : initialValue
    }
  }, [key, initialValue])

  const [storedValue, setStoredValue] = useState<T>(readValue)

  const setValue = useCallback(
    (value: T | ((prev: T) => T)) => {
      try {
        setStoredValue((prev) => {
          const next = value instanceof Function ? value(prev) : value
          if (typeof window !== 'undefined') {
            window.localStorage.setItem(key, JSON.stringify(next))
            // Dispatch a local custom event so same-window listeners update too if needed
            window.dispatchEvent(new StorageEvent('storage', { key, newValue: JSON.stringify(next) }))
          }
          return next
        })
      } catch {
        // quota exceeded or private mode
      }
    },
    [key]
  )

  const removeValue = useCallback(() => {
    try {
      if (typeof window !== 'undefined') {
        window.localStorage.removeItem(key)
        window.dispatchEvent(new StorageEvent('storage', { key, newValue: null }))
      }
      setStoredValue(initialValue instanceof Function ? initialValue() : initialValue)
    } catch {
      // storage disabled
    }
  }, [key, initialValue])

  useEffect(() => {
    const handleStorageChange = (e: StorageEvent) => {
      if (e.key === key) {
        if (e.newValue === null) {
          setStoredValue(initialValue instanceof Function ? initialValue() : initialValue)
        } else {
          try {
            setStoredValue(JSON.parse(e.newValue) as T)
          } catch {
            // invalid json
          }
        }
      }
    }

    window.addEventListener('storage', handleStorageChange)
    return () => window.removeEventListener('storage', handleStorageChange)
  }, [key, initialValue])

  return [storedValue, setValue, removeValue]
}
