export default interface CreatePasteRequest {
  alias?: string
  title: string
  content: string
  privacy: string
  expiredAt?: string
}
