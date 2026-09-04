import tailwindcss from '@tailwindcss/vite'
import react, { reactCompilerPreset } from '@vitejs/plugin-react'
import babel from '@rolldown/plugin-babel'
import { defineConfig, type Plugin } from 'vite'
import type { IncomingMessage, ServerResponse } from 'node:http'
import path from 'path'
import { fileURLToPath } from 'url'

// ponytail: zero-dependency Vite mock API middleware for /api/pastes simulating Spring Boot backend
function mockApiPlugin(): Plugin {
  interface MockPaste {
    pasteId: string
    title: string
    alias?: string
    content: string
    syntax?: string
    privacy?: string
    createdAt: string
    expiredAt?: string
  }

  const pastes: MockPaste[] = [
    {
      pasteId: 'demo-welcome',
      title: 'Welcome to Pastebin',
      content: '// Welcome to Pastebin!\n// Paste, share, and manage code snippets securely.\nconsole.log("Hello, world!");',
      syntax: 'javascript',
      privacy: 'public',
      createdAt: new Date(Date.now() - 3600000).toISOString(),
    },
    {
      pasteId: 'demo-quicksort',
      title: 'Quick Sort in TypeScript',
      content: 'function quickSort(arr: number[]): number[] {\n  if (arr.length <= 1) return arr;\n  const pivot = arr[arr.length - 1];\n  const left = arr.filter((x, i) => x <= pivot && i < arr.length - 1);\n  const right = arr.filter(x => x > pivot);\n  return [...quickSort(left), pivot, ...quickSort(right)];\n}',
      syntax: 'typescript',
      privacy: 'public',
      createdAt: new Date(Date.now() - 7200000).toISOString(),
    },
  ]

  function readBody(req: IncomingMessage): Promise<string> {
    return new Promise((resolve, reject) => {
      let data = ''
      req.on('data', (chunk) => {
        data += chunk
      })
      req.on('end', () => resolve(data))
      req.on('error', reject)
    })
  }

  function sendJson(res: ServerResponse, status: number, body: unknown) {
    res.statusCode = status
    res.setHeader('Content-Type', 'application/json; charset=utf-8')
    res.setHeader('Access-Control-Allow-Origin', '*')
    res.setHeader('Access-Control-Allow-Methods', 'GET, POST, PATCH, DELETE, OPTIONS')
    res.setHeader('Access-Control-Allow-Headers', 'Content-Type, Authorization')
    res.end(JSON.stringify(body))
  }

  const middleware = async (req: IncomingMessage, res: ServerResponse, next: () => void) => {
    const rawUrl = req.url || ''
    if (!rawUrl.startsWith('/api')) {
      return next()
    }

    if (req.method === 'OPTIONS') {
      res.statusCode = 204
      res.setHeader('Access-Control-Allow-Origin', '*')
      res.setHeader('Access-Control-Allow-Methods', 'GET, POST, PATCH, DELETE, OPTIONS')
      res.setHeader('Access-Control-Allow-Headers', 'Content-Type, Authorization')
      res.end()
      return
    }

    const url = new URL(rawUrl, 'http://localhost')
    const pathname = url.pathname.replace(/\/$/, '')

    try {
      // POST /api/pastes - Create paste
      if (req.method === 'POST' && pathname === '/api/pastes') {
        const raw = await readBody(req)
        const dto = raw ? JSON.parse(raw) : {}

        if (!dto.content || typeof dto.content !== 'string' || !dto.content.trim()) {
          sendJson(res, 400, { code: 400, message: 'Content is required', data: null })
          return
        }

        const pasteId = 'p_' + Math.random().toString(36).slice(2, 9)
        const newPaste: MockPaste = {
          pasteId,
          title: (dto.title && dto.title.trim()) || 'Untitled',
          alias: dto.alias?.trim() || undefined,
          content: dto.content,
          syntax: dto.syntax || 'plaintext',
          privacy: dto.privacy || 'public',
          createdAt: new Date().toISOString(),
          expiredAt: dto.expiredAt || undefined,
        }

        pastes.unshift(newPaste)
        sendJson(res, 200, { code: 200, message: 'success', data: newPaste })
        return
      }

      // GET /api/pastes/own - List user pastes
      if (req.method === 'GET' && pathname === '/api/pastes/own') {
        const page = Math.max(1, parseInt(url.searchParams.get('page') || '1', 10))
        const size = Math.max(1, parseInt(url.searchParams.get('size') || '10', 10))
        const start = (page - 1) * size
        const items = pastes.slice(start, start + size)

        sendJson(res, 200, {
          code: 200,
          message: 'success',
          data: {
            items,
            total: pastes.length,
            page,
            pageSize: size,
          },
        })
        return
      }

      // GET /api/pastes/:id - Get paste by ID or alias
      if (req.method === 'GET' && pathname.startsWith('/api/pastes/')) {
        const id = decodeURIComponent(pathname.slice('/api/pastes/'.length))
        const item = pastes.find((p) => p.pasteId === id || p.alias === id)
        if (!item) {
          sendJson(res, 404, { code: 404, message: 'Paste not found', data: null })
          return
        }
        sendJson(res, 200, { code: 200, message: 'success', data: item })
        return
      }

      // PATCH /api/pastes/:id - Update paste
      if (req.method === 'PATCH' && pathname.startsWith('/api/pastes/')) {
        const id = decodeURIComponent(pathname.slice('/api/pastes/'.length))
        const index = pastes.findIndex((p) => p.pasteId === id || p.alias === id)
        if (index === -1) {
          sendJson(res, 404, { code: 404, message: 'Paste not found', data: null })
          return
        }
        const raw = await readBody(req)
        const patch = raw ? JSON.parse(raw) : {}
        pastes[index] = { ...pastes[index], ...patch }
        sendJson(res, 200, { code: 200, message: 'success', data: pastes[index] })
        return
      }

      // DELETE /api/pastes/:id - Delete paste
      if (req.method === 'DELETE' && pathname.startsWith('/api/pastes/')) {
        const id = decodeURIComponent(pathname.slice('/api/pastes/'.length))
        const index = pastes.findIndex((p) => p.pasteId === id || p.alias === id)
        if (index === -1) {
          sendJson(res, 404, { code: 404, message: 'Paste not found', data: null })
          return
        }
        pastes.splice(index, 1)
        sendJson(res, 200, { code: 200, message: 'success', data: null })
        return
      }

      sendJson(res, 404, { code: 404, message: `Mock API route not found: ${req.method} ${pathname}`, data: null })
    } catch (err) {
      sendJson(res, 500, {
        code: 500,
        message: err instanceof Error ? err.message : 'Internal mock error',
        data: null,
      })
    }
  }

  return {
    name: 'mock-api',
    configureServer(server) {
      server.middlewares.use(middleware)
    },
    configurePreviewServer(server) {
      server.middlewares.use(middleware)
    },
  }
}

// https://vite.dev/config/
export default defineConfig({
  resolve: {
    alias: {
      '@': path.resolve(path.dirname(fileURLToPath(import.meta.url)), './src'),
    },
  },
  plugins: [
    tailwindcss(),
    react(),
    babel({ presets: [reactCompilerPreset()] }),
    mockApiPlugin(),
  ],
})
