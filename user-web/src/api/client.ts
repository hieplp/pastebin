import ky, { HTTPError, type Options } from 'ky'

export class ApiError extends Error {
  status: number
  data?: unknown

  constructor(message: string, status: number, data?: unknown) {
    super(message)
    this.name = 'ApiError'
    this.status = status
    this.data = data
  }
}

const rawBase = import.meta.env.VITE_API_URL || '/api'
export const apiPrefix = (
  rawBase.startsWith('http')
    ? rawBase
    : typeof window !== 'undefined'
      ? new URL(rawBase, window.location.origin).toString()
      : rawBase
).replace(/\/$/, '')

// ponytail: lightweight Ky HTTP client configured with cookie credentials and backend response unwrapping
export const api = ky.create({
  prefix: apiPrefix,
  credentials: 'include',
  hooks: {
    afterResponse: [
      async ({ response }) => {
        if (!response.ok || response.status === 204) return
        const ct = response.headers.get('content-type') ?? ''
        if (!ct.includes('application/json')) return
        const json: unknown = await response.json()
        const unwrapped =
          json && typeof json === 'object' && 'data' in json
            ? (json as Record<string, unknown>).data
            : json
        return new Response(JSON.stringify(unwrapped), response)
      },
    ],
    beforeError: [
      ({ error }) => {
        if (error instanceof HTTPError) {
          const data = error.data
          const message =
            data &&
            typeof data === 'object' &&
            'message' in data &&
            typeof data.message === 'string'
              ? data.message
              : error.message
          return new ApiError(message, error.response.status, data)
        }
        return error
      },
    ],
  },
})

export async function request<T>(
  path: string,
  options: Options = {},
): Promise<T> {
  const cleanPath = path.startsWith('/') ? path.slice(1) : path
  const response = await api(cleanPath, options)
  if (response.status === 204) {
    return undefined as unknown as T
  }
  return response.json<T>()
}
