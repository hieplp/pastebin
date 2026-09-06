import { useLocation, matchPasteRoute } from '@/lib/router'
import { CreatePastePage, DetailPastePage } from '@/pages'

export function App() {
  const { pathname } = useLocation()
  const pasteRoute = matchPasteRoute(pathname)

  if (pasteRoute) {
    return <DetailPastePage pasteId={pasteRoute.pasteId} />
  }

  return <CreatePastePage />
}

export default App
