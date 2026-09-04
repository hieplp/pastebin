import { api } from './client'
import type { CreatePasteDto, PasteItem, PaginatedResponse } from './types'

// ponytail: paste REST API endpoints mapped directly to Spring Boot backend via Ky
export const pasteApi = {
  create(data: CreatePasteDto): Promise<PasteItem> {
    return api.post('pastes', { json: data }).json<PasteItem>()
  },

  getOwn(params?: { page?: number; size?: number }): Promise<PaginatedResponse<PasteItem>> {
    return api.get('pastes/own', { searchParams: params }).json<PaginatedResponse<PasteItem>>()
  },

  getById(pasteId: string | number): Promise<PasteItem> {
    return api.get(`pastes/${pasteId}`).json<PasteItem>()
  },
}
