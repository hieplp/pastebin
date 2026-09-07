import { useRef } from 'react'
import { AppLayout } from '@/components/layout'
import {
  EditorArea,
  EditorContainer,
  EditorToolbar,
  PasteActions,
  UploadedFiles,
} from '@/pages/CreatePastePage/components'
import { useFileUpload } from '@/hooks'
import { getFileExtension } from '@/utils'
import { EXTENSION_MAP } from '@/constants.ts'
import { usePasteStore, useAlertStore } from '@/stores'
import { navigate } from '@/lib/router'
import type { UploadedFile } from '@/types'

export function CreatePastePage() {
  /* ---- Hooks ---- */
  const { draft, setDraft, clearDraft, createPaste, isLoading } =
    usePasteStore()
  const { showSuccessAlert, showErrorAlert } = useAlertStore()
  const { files, readFiles, clearFiles, removeFile } = useFileUpload()
  const fileInputRef = useRef<HTMLInputElement>(null)

  /* ---- States ---- */
  const canClear = Boolean(draft.content || draft.title || files.length > 0)
  const canSubmit =
    Boolean(draft.content.trim() || files.length > 0) && !isLoading

  /* ---- Functions ---- */
  function onClear() {
    clearDraft()
    clearFiles()
  }

  function computeExpiration(expiration: string) {
    if (expiration === 'burn') {
      return { burnAfterRead: true, expiredAt: undefined }
    }
    const durations: Record<string, number> = {
      '10m': 10 * 60 * 1000,
      '1h': 60 * 60 * 1000,
      '1d': 24 * 60 * 60 * 1000,
      '1w': 7 * 24 * 60 * 60 * 1000,
    }
    const duration = durations[expiration]
    if (duration) {
      return {
        expiredAt: new Date(Date.now() + duration).toISOString(),
        burnAfterRead: false,
      }
    }
    return { burnAfterRead: false, expiredAt: undefined }
  }

  async function onSubmit() {
    if (isLoading) return

    // ponytail: files travel as separate attachments; content is editor text only
    const content = draft.content.trim()
    if (!content && files.length === 0) return

    const { expiredAt, burnAfterRead } = computeExpiration(draft.expiration)

    try {
      const created = await createPaste(
        {
          ...draft,
          content,
          expiredAt,
          burnAfterRead,
        },
        files.flatMap((f) => (f.file ? [f.file] : [])),
      )
      onClear()
      showSuccessAlert(
        `Paste "${draft.title || 'Untitled'}" created successfully!`,
      )
      navigate(`/pastes/${created.alias || created.pasteId}`)
    } catch (err) {
      showErrorAlert(
        err instanceof Error ? err.message : 'Failed to create paste',
      )
    }
  }

  function onSelectFile(file: UploadedFile) {
    if (file.content === undefined) return
    const ext = getFileExtension(file.name)
    setDraft((prev) => ({
      ...prev,
      title: file.name,
      content: file.content ?? '',
      syntax: EXTENSION_MAP[ext] || prev.syntax,
    }))
  }

  /* ---- Render ---- */
  return (
    <AppLayout>
      {/* Editor Container */}
      <EditorContainer
        draft={draft}
        onDropFiles={(files) => void readFiles(files)}
      >
        {/* Editor Toolbar */}
        <EditorToolbar
          title={draft.title}
          syntax={draft.syntax}
          expiration={draft.expiration}
          onChange={(field, value: object) => {
            setDraft((prev) => ({ ...prev, [field]: value }))
          }}
          onUploadClick={() => {
            fileInputRef.current?.click()
          }}
        />

        {/* Attached Files Bar */}
        <input
          ref={fileInputRef}
          type="file"
          multiple
          className="hidden"
          onChange={(e) => {
            void readFiles(e.target.files)
            if (e.target) e.target.value = ''
          }}
        />
        <UploadedFiles
          files={files}
          onClear={clearFiles}
          onRemove={removeFile}
          onSelect={onSelectFile}
        />

        {/* Editor Area */}
        <EditorArea
          draft={draft}
          setDraft={setDraft}
          onSubmit={() => void onSubmit()}
        />
      </EditorContainer>

      {/* Bottom Actions */}
      <PasteActions
        clear={{
          onClick: onClear,
          enabled: canClear,
        }}
        submit={{
          onClick: () => void onSubmit(),
          enabled: canSubmit,
          loading: isLoading,
        }}
      />
    </AppLayout>
  )
}
