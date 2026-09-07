export interface UploadedFile {
  fileId?: string
  name: string
  size: number
  content?: string
  file?: File
}

export interface ProcessedUpload {
  files: UploadedFile[]
  content: string
  title?: string
  syntax?: string
}
