import type { UploadedFile } from './file.ts'

export interface DraftPaste {
  title: string
  content: string
  syntax: string
  expiration: string
}

export interface CreatePasteRequest {
  title: string
  content: string
  syntax?: string
  privacy?: string
  alias?: string
  expiredAt?: string
  burnAfterRead?: boolean
}

export interface Paste {
  pasteId: number | string
  title: string
  alias?: string
  content: string
  syntax?: string
  privacy?: string
  createdAt?: string
  expiredAt?: string
  files?: UploadedFile[]
}
