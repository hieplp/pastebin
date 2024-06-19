import type CreatePasteResponse from '@/types/payload/paste/create.res'
import type CreatePasteRequest from '@/types/payload/paste/create.req'
import { ApiUtil } from '@/utils/api.util'

interface IPasteService {
  create: (request: CreatePasteRequest) => Promise<CreatePasteResponse>
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
  }
}

export default PasteService
