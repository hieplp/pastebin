import { api, apiPrefix } from './client.ts'
import type { UploadedFile } from '@/types'

// ponytail: file endpoints mapped directly to Spring Boot backend via Ky
export const fileApi = {
  getFile(fileId: string): Promise<UploadedFile> {
    return api.get(`files/${fileId}`).json<UploadedFile>()
  },
  downloadUrl(fileId: string): string {
    return `${apiPrefix}/files/${encodeURIComponent(fileId)}/download`
  },
}
