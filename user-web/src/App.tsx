import { useLocation, matchPasteRoute } from './lib/router'
import { CreatePastePage } from './pages/CreatePastePage'
import { PasteDetailPage } from './pages/PasteDetailPage'

export function App() {
  const { pathname } = useLocation()
  const pasteRoute = matchPasteRoute(pathname)

  if (pasteRoute) {
    return <PasteDetailPage pasteId={pasteRoute.pasteId} />
  }

  return <CreatePastePage />
}

export default App
