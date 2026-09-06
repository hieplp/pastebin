export interface UploadedFile {
  name: string
  size: number
  content?: string
}

export interface ProcessedUpload {
  files: UploadedFile[]
  content: string
  title?: string
  syntax?: string
}
