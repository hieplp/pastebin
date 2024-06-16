import type LoginRequest from '@/types/payload/auth/login.req'
import type LoginResponse from '@/types/payload/auth/login.res'
import type RegisterRequest from '@/types/payload/auth/register.req'
import type RegisterResponse from '@/types/payload/auth/register.res'
import { NonAuthApiUtil } from '@/utils/api.util'

interface IAuthService {
  register: (request: RegisterRequest) => Promise<RegisterResponse>
  login: (request: LoginRequest) => Promise<LoginResponse>
}

const AuthService: IAuthService = {
  register: (request): Promise<RegisterResponse> => {
    return new Promise((resolve, reject) => {
      NonAuthApiUtil.post('/auth/register', request)
        .then((response) => {
          resolve(response)
        })
        .catch((error) => {
          reject(error)
        })
    })
  },

  login: (request): Promise<LoginResponse> => {
    return new Promise((resolve, reject) => {
      NonAuthApiUtil.post('/auth/login', request)
        .then((response) => {
          resolve(response)
        })
        .catch((error) => {
          reject(error)
        })
    })
  }
}

export default AuthService
