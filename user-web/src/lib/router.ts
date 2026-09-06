import { useState, useEffect, useCallback } from 'react'

export interface LocationState {
  pathname: string
  search: string
}

const NAVIGATE_EVENT = 'pastebin:navigate'

export function navigate(to: string, options: { replace?: boolean } = {}) {
  const url = new URL(to, window.location.origin)
  const path = url.pathname + url.search + url.hash

  if (options.replace) {
    window.history.replaceState(null, '', path)
  } else {
    window.history.pushState(null, '', path)
  }

  window.dispatchEvent(new Event(NAVIGATE_EVENT))
  window.scrollTo({ top: 0, behavior: 'instant' as ScrollBehavior })
}

// ponytail: native history-backed router hook with popstate and internal link interception
export function useLocation(): LocationState & { navigate: typeof navigate } {
  const [location, setLocation] = useState<LocationState>(() => ({
    pathname: typeof window !== 'undefined' ? window.location.pathname : '/',
    search: typeof window !== 'undefined' ? window.location.search : '',
  }))

  const updateLocation = useCallback(() => {
    setLocation({
      pathname: window.location.pathname,
      search: window.location.search,
    })
  }, [])

  useEffect(() => {
    window.addEventListener('popstate', updateLocation)
    window.addEventListener(NAVIGATE_EVENT, updateLocation)

    // Intercept internal <a> clicks for SPA navigation
    const handleLinkClick = (e: MouseEvent) => {
      if (
        e.defaultPrevented ||
        e.button !== 0 ||
        e.metaKey ||
        e.ctrlKey ||
        e.shiftKey ||
        e.altKey
      ) {
        return
      }

      const anchor = (e.target as HTMLElement).closest('a')
      if (!anchor) return

      const href = anchor.getAttribute('href')
      if (
        !href ||
        href.startsWith('http:') ||
        href.startsWith('https:') ||
        href.startsWith('//') ||
        href.startsWith('#') ||
        href.startsWith('mailto:') ||
        anchor.target === '_blank'
      ) {
        return
      }

      e.preventDefault()
      navigate(href)
    }

    document.addEventListener('click', handleLinkClick)

    return () => {
      window.removeEventListener('popstate', updateLocation)
      window.removeEventListener(NAVIGATE_EVENT, updateLocation)
      document.removeEventListener('click', handleLinkClick)
    }
  }, [updateLocation])

  return {
    pathname: location.pathname,
    search: location.search,
    navigate,
  }
}

export function matchPasteRoute(pathname: string): { pasteId: string } | null {
  const match = pathname.match(/^\/(?:pastes|p)\/([^/]+)\/?$/)
  if (!match) return null
  return { pasteId: decodeURIComponent(match[1]) }
}
