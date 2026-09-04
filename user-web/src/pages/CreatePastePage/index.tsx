import { AppLayout } from '../../components/layout'
import { Alert } from '../../components/ui'
import {
  PasteToolbar,
  UploadedFiles,
  EditorStatusBar,
  PasteActions,
  DragOverlay,
} from './components'
import { usePasteForm } from './hooks'
import type { PasteItem } from '../../api/types'
import { navigate } from '../../lib/router'

export interface CreatePastePageProps {
  onSuccess?: (paste: PasteItem) => void
}

export function CreatePastePage({ onSuccess }: CreatePastePageProps = {}) {
  const handleSuccess = (paste: PasteItem) => {
    if (onSuccess) {
      onSuccess(paste)
    } else {
      const identifier = paste.alias || paste.pasteId
      navigate(`/pastes/${identifier}?created=true`)
    }
  }

  const {
    form,
    files,
    isDragging,
    isSubmitting,
    message,
    messageType,
    canClear,
    canSubmit,
    fileInputRef,
    dragProps,
    handleKeyDown,
    handleTitleChange,
    handleContentChange,
    handleSyntaxChange,
    handleExpirationChange,
    handleUploadClick,
    handleFileInputChange,
    handleRemoveFile,
    handleSelectFile,
    handleClearFiles,
    handleClear,
    handleSubmit,
    setMessage,
  } = usePasteForm({ onSuccess: handleSuccess })

  return (
    <AppLayout
      navbarProps={{
        badge: 'new paste',
        actions: (
          <span className="text-xs text-zinc-500 font-mono">
            press <kbd className="px-1.5 py-0.5 rounded bg-zinc-100 dark:bg-zinc-800 border border-zinc-300 dark:border-zinc-700 text-zinc-700 dark:text-zinc-300">⌘</kbd> + <kbd className="px-1.5 py-0.5 rounded bg-zinc-100 dark:bg-zinc-800 border border-zinc-300 dark:border-zinc-700 text-zinc-700 dark:text-zinc-300">Enter</kbd> to save
          </span>
        ),
      }}
    >
      {/* Banner Alert */}
      <Alert
        message={message ?? undefined}
        variant={messageType}
        onDismiss={() => setMessage(null)}
      />

      {/* Hidden Native File Input */}
      <input
        ref={fileInputRef}
        type="file"
        multiple
        className="hidden"
        onChange={handleFileInputChange}
      />

      {/* Editor Container */}
      <div
        {...dragProps}
        className={`group relative flex-1 flex flex-col rounded-2xl border transition-all duration-200 shadow-2xl shadow-black/40 overflow-hidden ${
          isDragging
            ? 'border-primary-500 bg-primary-950/20 ring-4 ring-primary-500/20'
            : 'border-zinc-200 dark:border-zinc-800 bg-white/70 dark:bg-zinc-900/40 hover:border-zinc-300 dark:hover:border-zinc-700/80 backdrop-blur-xl shadow-xl dark:shadow-2xl shadow-zinc-950/5 dark:shadow-black/40'
        }`}
      >
        {/* Drag Overlay Indicator */}
        <DragOverlay visible={isDragging} />

        {/* Integrated Toolbar */}
        <PasteToolbar
          title={form.title}
          syntax={form.syntax}
          expiration={form.expiration}
          onTitleChange={handleTitleChange}
          onSyntaxChange={handleSyntaxChange}
          onExpirationChange={handleExpirationChange}
          onUploadClick={handleUploadClick}
        />

        {/* Attached Files Bar */}
        <UploadedFiles
          files={files}
          onClear={handleClearFiles}
          onRemove={handleRemoveFile}
          onSelectFile={handleSelectFile}
        />

        {/* Editor Area */}
        <div className="relative flex-1 flex flex-col">
          <textarea
            value={form.content}
            onChange={(e) => handleContentChange(e.target.value)}
            onKeyDown={handleKeyDown}
            placeholder="Paste or write your text or code here... (or drag & drop files)"
            spellCheck={false}
            className="flex-1 w-full p-4 sm:p-5 bg-transparent resize-y min-h-105 font-mono text-[13px] leading-6 text-zinc-900 dark:text-zinc-100 placeholder:text-zinc-400 dark:placeholder:text-zinc-650 focus:outline-none selection:bg-primary-500/20"
          />
        </div>

        {/* Editor Status Footer */}
        <EditorStatusBar content={form.content} syntax={form.syntax} />
      </div>

      {/* Bottom Actions */}
      <PasteActions
        canClear={canClear}
        canSubmit={canSubmit}
        submitting={isSubmitting}
        onClear={handleClear}
        onSubmit={handleSubmit}
      />
    </AppLayout>
  )
}
