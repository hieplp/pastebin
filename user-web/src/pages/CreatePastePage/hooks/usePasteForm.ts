import {
  useState,
  useRef,
  useCallback,
  useEffect,
  type KeyboardEvent,
  type ChangeEvent,
  type RefObject,
} from 'react'
import { usePasteStore, DEFAULT_DRAFT, type PasteDraft } from '@/store'
import type { PasteItem } from '@/api/types'
import { EXTENSION_MAP } from '@/constants'
import { useFileUpload, type ProcessedUpload, type DragProps } from './useFileUpload'
import type { UploadedFile } from '../components'

export type PasteFormData = PasteDraft

export interface UsePasteFormOptions {
  initialValues?: Partial<PasteFormData>
  persistDraft?: boolean
  storageKey?: string
  onSubmit?: (data: PasteFormData) => Promise<PasteItem | void> | PasteItem | void
  onSuccess?: (paste: PasteItem) => void
}

export interface UsePasteFormReturn {
  form: PasteFormData
  files: UploadedFile[]
  isDragging: boolean
  isSubmitting: boolean
  message: string | null
  messageType: 'success' | 'error'
  error: string | null
  canClear: boolean
  canSubmit: boolean
  fileInputRef: RefObject<HTMLInputElement | null>
  dragProps: DragProps
  handleKeyDown: (e: KeyboardEvent<HTMLTextAreaElement>) => void
  handleTitleChange: (title: string) => void
  handleContentChange: (content: string) => void
  handleSyntaxChange: (syntax: string) => void
  handleExpirationChange: (expiration: string) => void
  handleUploadClick: () => void
  handleFileInputChange: (e: ChangeEvent<HTMLInputElement>) => void
  handleRemoveFile: (index: number) => void
  removeFile: (index: number) => void
  handleSelectFile: (file: UploadedFile) => void
  selectFile: (file: UploadedFile) => void
  handleClearFiles: () => void
  handleClear: () => void
  handleSubmit: () => Promise<void>
  setMessage: (message: string | null, type?: 'success' | 'error') => void
}

// ponytail: paste form controller connected to Zustand paste store with draft persistence
export function usePasteForm(options: UsePasteFormOptions = {}): UsePasteFormReturn {
  const {
    initialValues,
    persistDraft = true,
    onSubmit,
    onSuccess,
  } = options

  const draft = usePasteStore((s) => s.draft)
  const setDraft = usePasteStore((s) => s.setDraft)
  const resetDraft = usePasteStore((s) => s.resetDraft)
  const createPasteInStore = usePasteStore((s) => s.createPaste)

  const [localForm, setLocalForm] = useState<PasteFormData>({ ...DEFAULT_DRAFT, ...initialValues })
  const form = persistDraft ? draft : localForm
  const setForm = persistDraft ? setDraft : setLocalForm

  useEffect(() => {
    if (initialValues) {
      setForm((prev) => ({ ...prev, ...initialValues }))
    }
  }, [initialValues, setForm])

  const [isSubmitting, setIsSubmitting] = useState(false)
  const [message, setMessageState] = useState<string | null>(null)
  const [messageType, setMessageType] = useState<'success' | 'error'>('success')
  const fileInputRef = useRef<HTMLInputElement>(null)

  const setMessage = useCallback((msg: string | null, type: 'success' | 'error' = 'success') => {
    setMessageState(msg)
    setMessageType(type)
  }, [])

  const handleUploadComplete = useCallback(
    (upload: ProcessedUpload) => {
      setForm((prev) => ({
        ...prev,
        ...(!prev.title && upload.title ? { title: upload.title } : {}),
        syntax: upload.syntax || prev.syntax,
      }))
    },
    [setForm]
  )

  const { files, isDragging, readFiles, removeFile, clearFiles, dragProps } = useFileUpload({
    onUpload: handleUploadComplete,
  })

  const handleTitleChange = useCallback(
    (title: string) => setForm((prev) => ({ ...prev, title })),
    [setForm]
  )

  const handleContentChange = useCallback(
    (content: string) => setForm((prev) => ({ ...prev, content })),
    [setForm]
  )

  const handleSyntaxChange = useCallback(
    (syntax: string) => setForm((prev) => ({ ...prev, syntax })),
    [setForm]
  )

  const handleExpirationChange = useCallback(
    (expiration: string) => setForm((prev) => ({ ...prev, expiration })),
    [setForm]
  )

  const handleUploadClick = useCallback(() => {
    fileInputRef.current?.click()
  }, [])

  const handleFileInputChange = useCallback(
    (e: ChangeEvent<HTMLInputElement>) => {
      void readFiles(e.target.files)
      if (e.target) e.target.value = ''
    },
    [readFiles]
  )

  const handleSelectFile = useCallback(
    (file: UploadedFile) => {
      if (file.content === undefined) return
      const ext = file.name.split('.').pop()?.toLowerCase() ?? ''
      setForm((prev) => ({
        ...prev,
        title: file.name,
        content: file.content ?? '',
        syntax: EXTENSION_MAP[ext] || prev.syntax,
      }))
    },
    [setForm]
  )

  const handleClear = useCallback(() => {
    resetDraft()
    setLocalForm(DEFAULT_DRAFT)
    clearFiles()
    setMessage(null)
  }, [resetDraft, clearFiles, setMessage])

  const handleSubmit = useCallback(async () => {
    const effectiveContent =
      form.content.trim() ||
      (files.length === 1
        ? files[0].content ?? ''
        : files.map((f) => `// --- ${f.name} ---\n${f.content ?? ''}`).join('\n\n'))

    if (!effectiveContent.trim() || isSubmitting) return

    const submissionData = { ...form, content: effectiveContent }

    setIsSubmitting(true)
    setMessage(null)
    try {
      let created: PasteItem | void
      if (onSubmit) {
        created = await onSubmit(submissionData)
      } else {
        created = await createPasteInStore(submissionData)
      }
      setMessage(`Paste "${form.title || 'Untitled'}" created successfully!`, 'success')
      if (persistDraft) resetDraft()
      if (created) {
        onSuccess?.(created)
      }
    } catch (err) {
      setMessage(err instanceof Error ? err.message : 'Failed to create paste', 'error')
    } finally {
      setIsSubmitting(false)
    }
  }, [form, files, isSubmitting, onSubmit, onSuccess, createPasteInStore, persistDraft, resetDraft, setMessage])
  const handleKeyDown = useCallback(
    (e: KeyboardEvent<HTMLTextAreaElement>) => {
      // Tab key indents 2 spaces natively
      if (e.key === 'Tab') {
        e.preventDefault()
        const textarea = e.currentTarget
        const start = textarea.selectionStart
        const end = textarea.selectionEnd
        setForm((prev) => ({
          ...prev,
          content: prev.content.substring(0, start) + '  ' + prev.content.substring(end),
        }))
        requestAnimationFrame(() => {
          textarea.selectionStart = textarea.selectionEnd = start + 2
        })
        return
      }

      // Cmd/Ctrl + Enter submits
      if ((e.metaKey || e.ctrlKey) && e.key === 'Enter') {
        e.preventDefault()
        void handleSubmit()
      }
    },
    [handleSubmit, setForm]
  )

  return {
    form,
    files,
    isDragging,
    isSubmitting,
    message,
    messageType,
    error: messageType === 'error' ? message : null,
    canClear: Boolean(form.content || form.title || files.length > 0),
    canSubmit: Boolean(form.content.trim() || files.length > 0),
    fileInputRef,
    dragProps,
    handleKeyDown,
    handleTitleChange,
    handleContentChange,
    handleSyntaxChange,
    handleExpirationChange,
    handleUploadClick,
    handleFileInputChange,
    handleRemoveFile: removeFile,
    removeFile,
    handleSelectFile,
    selectFile: handleSelectFile,
    handleClearFiles: clearFiles,
    handleClear,
    handleSubmit,
    setMessage,
  }
}
