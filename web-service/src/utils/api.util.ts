import axios, {
  AxiosError,
  type AxiosRequestConfig,
  type AxiosResponse,
  type InternalAxiosRequestConfig
} from 'axios'
import SuccessCode from '@/constants/SuccessCode'

const BASE_URL = 'http://localhost:8080/api'
const WEB_URL = 'http://localhost:5173'

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
    'Content-Type': 'application/json',
    'Access-Control-Allow-Origin': WEB_URL
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
  const data = response.data
  if (SuccessCode.SUCCESS === data.code) {
    return Promise.resolve(data.data)
  }

  return Promise.reject(data.code)
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

  put<T = any>(url: string, data?: any, config?: AxiosRequestConfig): Promise<T>

  delete<T = any>(url: string, config?: AxiosRequestConfig): Promise<T>
}

const ApiUtil: IApiUtil = {
  get: async <T>(url: string, config?: AxiosRequestConfig): Promise<T> => {
    return instance
      .get<T>(url, {
        ...config,
        withCredentials: true
      })
      .then(handleSuccess)
      .catch(handleError)
  },

  post: async <T>(url: string, data?: any, config?: AxiosRequestConfig): Promise<T> => {
    return instance
      .post<T>(url, data, {
        ...config,
        withCredentials: true
      })
      .then(handleSuccess)
      .catch(handleError)
  },

  put: async <T>(url: string, data?: any, config?: AxiosRequestConfig): Promise<T> => {
    return instance
      .put<T>(url, data, {
        ...config,
        withCredentials: true
      })
      .then(handleSuccess)
      .catch(handleError)
  },

  delete: async <T>(url: string, config?: AxiosRequestConfig): Promise<T> => {
    return instance
      .delete<T>(url, {
        ...config,
        withCredentials: true
      })
      .then(handleSuccess)
      .catch(handleError)
  }
}

const NonAuthApiUtil: IApiUtil = {
  get: async <T>(url: string, config?: AxiosRequestConfig): Promise<T> => {
    return nonAuthInstance
      .get<T>(url, {
        ...config,
        withCredentials: true
      })
      .then(handleSuccess)
      .catch(handleError)
  },

  post: async <T>(url: string, data?: any, config?: AxiosRequestConfig): Promise<T> => {
    return nonAuthInstance
      .post<T>(url, data, {
        ...config,
        withCredentials: true
      })
      .then(handleSuccess)
      .catch(handleError)
  },

  put: async <T>(url: string, data?: any, config?: AxiosRequestConfig): Promise<T> => {
    return nonAuthInstance
      .put<T>(url, data, {
        ...config,
        withCredentials: true
      })
      .then(handleSuccess)
      .catch(handleError)
  },

  delete: async <T>(url: string, config?: AxiosRequestConfig): Promise<T> => {
    return nonAuthInstance
      .delete<T>(url, {
        ...config,
        withCredentials: true
      })
      .then(handleSuccess)
      .catch(handleError)
  }
}

export { ApiUtil, NonAuthApiUtil }
