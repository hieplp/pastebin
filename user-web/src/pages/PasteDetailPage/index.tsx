import { AppLayout } from '../../components/layout'
import { Alert } from '../../components/ui'
import {
  ShareUrlCard,
  PasteViewer,
  PasteLoading,
  PasteNotFound,
} from './components'
import { usePasteDetail } from './hooks'

export interface PasteDetailPageProps {
  pasteId: string
}

// ponytail: paste detail and share view orchestrating share card, viewer, and states
export function PasteDetailPage({ pasteId }: PasteDetailPageProps) {
  const {
    paste,
    isLoading,
    error,
    showCreatedAlert,
    dismissCreatedAlert,
    showRaw,
    toggleRaw,
    shareUrl,
    urlInputRef,
    copiedUrl,
    copiedContent,
    lines,
    handleCopyUrl,
    handleCopyContent,
  } = usePasteDetail({ pasteId })

  return (
    <AppLayout
      navbarProps={{
        badge: 'paste view',
        actions: (
          <a
            href="/"
            className="px-3.5 py-1.5 rounded-xl bg-primary-600 hover:bg-primary-500 text-white text-xs font-semibold flex items-center gap-1.5 transition shadow-sm active:scale-[0.98] cursor-pointer"
          >
            <span>+</span>
            <span>New Paste</span>
          </a>
        ),
      }}
    >
      {/* Creation Success Alert */}
      {showCreatedAlert && (
        <Alert
          variant="success"
          message="Paste created successfully! Your paste URL is ready to share."
          onDismiss={dismissCreatedAlert}
        />
      )}

      {/* Loading State */}
      {isLoading && !paste && <PasteLoading />}

      {/* Error / Not Found State */}
      {error && !paste && !isLoading && (
        <PasteNotFound message={error.message} />
      )}

      {/* Main Paste & URL Content */}
      {paste && (
        <div className="flex flex-col gap-4">
          <ShareUrlCard
            shareUrl={shareUrl}
            copied={copiedUrl}
            inputRef={urlInputRef}
            onCopy={handleCopyUrl}
          />

          <PasteViewer
            paste={paste}
            lines={lines}
            showRaw={showRaw}
            copiedContent={copiedContent}
            onToggleRaw={toggleRaw}
            onCopyContent={handleCopyContent}
          />
        </div>
      )}
    </AppLayout>
  )
}
