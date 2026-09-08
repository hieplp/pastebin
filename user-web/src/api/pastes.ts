import { api } from './client.ts'
import type { CreatePasteRequest } from '@/types'
import type {
  CreatePasteResponse,
  PasteItem,
  PaginatedResponse,
} from './types.ts'

// ponytail: paste REST API endpoints mapped directly to Spring Boot backend via Ky
export const pasteApi = {
  create(
    payload: CreatePasteRequest,
    files: File[] = [],
  ): Promise<CreatePasteResponse> {
    const body = new FormData()
    body.append(
      'request',
      new Blob(
        [
          JSON.stringify({
            title: payload.title,
            content: payload.content,
            syntax: (payload.syntax ?? 'plaintext').toUpperCase(),
            privacy: (payload.privacy ?? 'public').toUpperCase(),
            alias: payload.alias ? payload.alias.trim() : undefined,
            expiredAt: payload.expiredAt,
            burnAfterRead: payload.burnAfterRead ?? false,
          }),
        ],
        { type: 'application/json' },
      ),
    )
    files.forEach((file) => body.append('files', file))
    return api.post('pastes', { body }).json<CreatePasteResponse>()
  },

  getOwn(params?: {
    page?: number
    size?: number
  }): Promise<PaginatedResponse<PasteItem>> {
    return api
      .get('pastes/own', { searchParams: params })
      .json<PaginatedResponse<PasteItem>>()
  },
  getById(pasteId: string | number): Promise<PasteItem> {
    return api.get(`pastes/${pasteId}`).json<PasteItem>()
  },
  delete(pasteId: string | number): Promise<void> {
    return api.delete(`pastes/${pasteId}`).json()
  },
}
