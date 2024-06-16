import type UserType from '@/types/user.type'
import type TokenType from '@/types/token.type'

export default interface LoginResponse {
  user: UserType
  accessToken: TokenType
  refreshToken: TokenType
}
