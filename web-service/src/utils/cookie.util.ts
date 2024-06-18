interface ICookieUtil {
  save: (name: string, value: string, time: number) => void
  delete: (name: string) => void
  get: (name: string) => string | null | undefined
  exists: (name: string) => boolean
}

const CookieUtil: ICookieUtil = {
  save: (name: string, value: string, time: number) => {
    const date = new Date()
    date.setTime(time)
    document.cookie = `${name}=${value};expires=${date.toUTCString()};path=/`
  },

  delete: (name: string) => {
    document.cookie = `${name}=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;`
  },

  get: (name: string) => {
    const value = `; ${document.cookie}`
    const parts = value.split(`; ${name}=`)
    return parts.length === 2 ? parts.pop()?.split(';').shift() : null
  },

  exists: (name: string) => {
    return document.cookie.includes(name)
  }
}

export default CookieUtil
