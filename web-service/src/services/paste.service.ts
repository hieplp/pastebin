import type CreatePasteResponse from '@/types/payload/paste/create.res'
import type CreatePasteRequest from '@/types/payload/paste/create.req'
import { ApiUtil } from '@/utils/api.util'
import type GetOwnPastesRequest from '@/types/payload/paste/get-own.req'
import type CommonPaginationResponse from '@/types/payload/common-pagination.res'
import type PasteResponse from '@/types/payload/paste/paste.res'

interface IPasteService {
  create: (request: CreatePasteRequest) => Promise<CreatePasteResponse>,
  getOwnPastes: (params: GetOwnPastesRequest) => Promise<CommonPaginationResponse<PasteResponse>>
  getOwnPasteById: (pasteId: string) => Promise<PasteResponse>,
}

const PasteService: IPasteService = {
  create: (request): Promise<CreatePasteResponse> => {
    return new Promise((resolve, reject) => {
      ApiUtil.post('/pastes', request)
        .then((response) => {
          resolve(response)
        })
        .catch((error) => {
          reject(error)
        })
    })
  },

  getOwnPastes: (params): Promise<CommonPaginationResponse<PasteResponse>> => {
    return new Promise((resolve, reject) => {
      ApiUtil.get('/pastes/own', { params })
        .then((response) => {
          resolve(response)
        })
        .catch((error) => {
          reject(error)
        })
    })
  },

  getOwnPasteById: (pasteId: string): Promise<PasteResponse> => {
    return new Promise((resolve, reject) => {
      ApiUtil.get(`/pastes/${pasteId}`)
        .then((response) => {
          resolve(response)
        })
        .catch((error) => {
          reject(error)
        })
    })
  }
}

export default PasteService
