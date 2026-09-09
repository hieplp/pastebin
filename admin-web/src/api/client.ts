type Envelope<T> = { data?: T; message?: string }

export async function request<T>(
  path: string,
  init?: Omit<RequestInit, "body"> & { json?: unknown }
): Promise<T> {
  const { json, headers, ...rest } = init ?? {}
  const res = await fetch(`/api${path}`, {
    credentials: "include",
    ...rest,
    headers: {
      ...(json !== undefined ? { "Content-Type": "application/json" } : {}),
      ...headers,
    },
    body: json !== undefined ? JSON.stringify(json) : undefined,
  })
  const envelope: Envelope<T> | null = await res.json().catch(() => null)
  if (!res.ok) throw new Error(envelope?.message ?? "Request failed")
  return envelope?.data as T
}
