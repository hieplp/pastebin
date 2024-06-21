export default interface CommonPaginationResponse<T> {
  list: T[]
  total: number
}