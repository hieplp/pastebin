import axios, {
  AxiosError,
  type AxiosRequestConfig,
  type AxiosResponse,
  type InternalAxiosRequestConfig
} from 'axios'

const BASE_URL = 'http://localhost:8080/api'

const instance = axios.create({
  baseURL: BASE_URL,
  timeout: 1000,
  headers: {
    'Content-Type': 'application/json'
  }
})

const nonAuthInstance = axios.create({
  baseURL: BASE_URL,
  timeout: 1000,
  headers: {
    'Content-Type': 'application/json'
  }
})

instance.interceptors.request.use(
  async (config: InternalAxiosRequestConfig<any>) => {
    // Check if access token is available in local storage or store
    // Add access token to headers
    // config.headers.Authorization = getCookie(tokenConstant.accessToken);
    return config
  },
  (error: AxiosError) => {
    return Promise.reject(error)
  }
)

const handleSuccess = (response: AxiosResponse): any => {
  return response.data
}

const handleError = (error: AxiosError): any => {
  if (error.response) {
    return Promise.reject(error.response.data)
  } else {
    return Promise.reject(error)
  }
}

interface IApiUtil {
  get<T = any>(url: string, config?: AxiosRequestConfig): Promise<T>

  post<T = any>(url: string, data?: any, config?: AxiosRequestConfig): Promise<T>
}

const ApiUtil: IApiUtil = {
  get: async <T>(url: string, config?: AxiosRequestConfig): Promise<T> => {
    return instance.get<T>(url, config).then(handleSuccess).catch(handleError)
  },

  post: async <T>(url: string, data?: any, config?: AxiosRequestConfig): Promise<T> => {
    return instance.post<T>(url, data, config).then(handleSuccess).catch(handleError)
  }
}

const NonAuthApiUtil: IApiUtil = {
  get: async <T>(url: string, config?: AxiosRequestConfig): Promise<T> => {
    return nonAuthInstance.get<T>(url, config).then(handleSuccess).catch(handleError)
  },

  post: async <T>(url: string, data?: any, config?: AxiosRequestConfig): Promise<T> => {
    return nonAuthInstance.post<T>(url, data, config).then(handleSuccess).catch(handleError)
  }
}

export { ApiUtil, NonAuthApiUtil }
