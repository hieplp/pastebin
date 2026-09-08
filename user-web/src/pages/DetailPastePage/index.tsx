import { useEffect, useState, useMemo, useRef } from 'react'
import { AppLayout } from '@/components/layout'
import {
  DetailContainer,
  DetailToolbar,
  DetailCodeArea,
  DetailActions,
  DetailFiles,
  DetailNotFound,
} from './components'
import { usePasteStore } from '@/stores'
import { useLocation, matchPasteRoute } from '@/lib/router'
import { SpinnerIcon } from '@/components/icons'
import { parsePasteContent, getFileExtension } from '@/utils'
import { EXTENSION_MAP } from '@/constants.ts'

interface DetailPastePageProps {
  pasteId?: string
}

export function DetailPastePage({
  pasteId: propPasteId,
}: DetailPastePageProps) {
  /* ---- Hooks ---- */
  const { pathname } = useLocation()
  const routePasteId = matchPasteRoute(pathname)?.pasteId
  const activePasteId = propPasteId || routePasteId

  const { currentPaste, fetchPaste, fetchFileContent, isLoading, error } =
    usePasteStore()

  const [isRaw, setIsRaw] = useState(false)
  const fetchedId = useRef('')

  useEffect(() => {
    if (!activePasteId || fetchedId.current === activePasteId) return
    fetchedId.current = activePasteId
    void fetchPaste(activePasteId).catch(() => {})
  }, [activePasteId, fetchPaste])

  // Parse paste content into main text and attached files
  const parsed = useMemo(() => {
    if (!currentPaste) return { text: null, files: [] }
    return parsePasteContent(currentPaste.content, currentPaste.files)
  }, [currentPaste])

  const hasText = Boolean(parsed.text)
  const files = parsed.files

  // Active tab: 'text' | 'all' | filename
  const [activeTab, setActiveTab] = useState<string>('default')

  // Derive active tab, file, content, syntax, and title in one pass
  const view = useMemo(() => {
    const tab =
      activeTab === 'all'
        ? 'all'
        : activeTab === 'text' && hasText
          ? 'text'
          : files.some((f) => f.name === activeTab)
            ? activeTab
            : hasText
              ? 'text'
              : files[0]?.name || 'text'

    const file = files.find((f) => f.name === tab) || null

    const content =
      tab === 'all'
        ? currentPaste?.content || ''
        : tab === 'text'
          ? parsed.text || ''
          : file
            ? (file.content ?? '')
            : currentPaste?.content || ''

    const syntax =
      tab === 'all' || tab === 'text'
        ? currentPaste?.syntax || 'plaintext'
        : file
          ? EXTENSION_MAP[getFileExtension(file.name)] || 'plaintext'
          : currentPaste?.syntax || 'plaintext'

    const title =
      tab === 'all'
        ? currentPaste?.title
          ? `${currentPaste.title} (All)`
          : 'All Content'
        : tab === 'text'
          ? currentPaste?.title || 'Text'
          : file
            ? file.name
            : currentPaste?.title || 'Untitled'

    return { tab, file, content, syntax, title }
  }, [activeTab, hasText, files, parsed.text, currentPaste])

  const {
    tab: resolvedTab,
    file: activeFile,
    content: activeContent,
    syntax: activeSyntax,
    title: activeTitle,
  } = view

  const filePending = Boolean(activeFile?.fileId) && activeFile?.content == null

  useEffect(() => {
    if (!activeFile?.fileId || activeFile.content != null) {
      return
    }
    void fetchFileContent(activeFile.fileId)
  }, [activeFile, fetchFileContent])

  /* ---- Functions ---- */

  /* ---- Loading State ---- */
  if (isLoading || (!currentPaste && !error && activePasteId)) {
    return (
      <AppLayout>
        <div className="flex-1 flex flex-col items-center justify-center py-32 text-zinc-400">
          <SpinnerIcon className="h-8 w-8 animate-spin text-primary mb-4" />
          <p className="text-sm font-medium">Loading paste...</p>
        </div>
      </AppLayout>
    )
  }

  /* ---- Not Found / Error State ---- */
  if (error || !currentPaste || !activePasteId) {
    return <DetailNotFound message={error?.message} />
  }

  /* ---- Detail Page View ---- */
  return (
    <AppLayout>
      {/* Detail Container */}
      <DetailContainer
        content={activeContent}
        syntax={activeSyntax}
        createdAt={currentPaste.createdAt}
      >
        {/* Detail Toolbar */}
        <DetailToolbar
          paste={{
            ...currentPaste,
            title: activeTitle,
            syntax: activeSyntax,
            content: activeContent,
          }}
          onToggleRaw={() => setIsRaw((prev) => !prev)}
          isRaw={isRaw}
        />

        {/* Multi-file Tabs Bar (renders only when paste has attached files) */}
        <DetailFiles
          files={files}
          hasText={hasText}
          textTitle={currentPaste.title || 'Text'}
          activeTab={resolvedTab}
          onSelectTab={setActiveTab}
        />

        {/* Code View Area */}
        <DetailCodeArea
          content={activeContent}
          isRaw={isRaw}
          loading={filePending}
        />
      </DetailContainer>

      {/* Bottom Actions */}
      <DetailActions />
    </AppLayout>
  )
}
