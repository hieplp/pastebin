import { queryOptions } from "@tanstack/react-query"

import { hashPassword } from "@/lib/hash"
import { request } from "./client"

export async function existsRoot() {
  const data = await request<{ exists?: boolean }>("/roots/exists")
  return data?.exists === true
}

export const rootExistsQueryOptions = queryOptions({
  queryKey: ["roots", "exists"],
  queryFn: existsRoot,
  staleTime: Infinity,
})

export async function createRoot(body: {
  token: string
  username: string
  password: string
}) {
  await request("/roots", {
    method: "POST",
    json: { ...body, password: hashPassword(body.password) },
  })
}

export async function loginRoot(body: { username: string; password: string }) {
  return request<{ username: string }>("/roots/login", {
    method: "POST",
    json: { ...body, password: hashPassword(body.password) },
  })
}
