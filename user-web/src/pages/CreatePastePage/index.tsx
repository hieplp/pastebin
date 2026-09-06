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

  async function onSubmit() {
    if (isLoading) return

    const draftText = draft.content.trim()
    const filesText =
      files.length === 1 && !draftText
        ? (files[0].content ?? '')
        : files
            .map((f) => `// --- ${f.name} ---\n${f.content ?? ''}`)
            .join('\n\n')

    const content = [draftText, filesText].filter(Boolean).join('\n\n')
    if (!content.trim()) return

    try {
      const created = await createPaste({ ...draft, content })
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
