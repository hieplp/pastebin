import type { UploadedFile } from '@/types'

export interface CreatePasteDto {
  title: string
  content: string
  syntax?: string
  privacy?: 'public' | 'unlisted' | 'private' | string
  expiredAt?: string
  alias?: string
}

export interface PasteItem {
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

export interface ApiResponse<T = unknown> {
  code: number | string
  message?: string
  data: T
}

export interface PaginatedResponse<T> {
  items: T[]
  total: number
  page?: number
  pageSize?: number
}
