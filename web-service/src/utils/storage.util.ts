interface ILocalStorage {
  get(key: string): any

  set(key: string, value: any): void

  delete(key: string): void
}

export const LocalStorage: ILocalStorage = {
  get: (key: string) => {
    const value = window.localStorage.getItem(key)
    return value ? JSON.parse(value) : null
  },
  set: (key: string, value: any) => window.localStorage.setItem(key, JSON.stringify(value)),
  delete: (key: string) => window.localStorage.removeItem(key)
}
