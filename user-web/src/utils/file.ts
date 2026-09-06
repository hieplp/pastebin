import { EXTENSION_MAP } from '@/constants.ts'
import type { ProcessedUpload, UploadedFile } from '@/types'

// ponytail: native lastIndexOf — no regex, no array alloc; handles no-dot and dotfiles
export function getFileExtension(name: string): string {
  const i = name.lastIndexOf('.')
  return i <= 0 ? '' : name.slice(i + 1).toLowerCase()
}

// ponytail: native file.text() file-reading util with zero dependencies
export async function readFilesToUpload(
  fileList: FileList | File[] | null,
): Promise<ProcessedUpload | null> {
  if (!fileList || fileList.length === 0) return null

  const results = await Promise.all(
    Array.from(fileList).map(async (f) => ({
      name: f.name,
      size: f.size,
      text: await f.text(),
    })),
  )

  if (results.length === 1) {
    const file = results[0]
    const ext = getFileExtension(file.name)
    return {
      files: [{ name: file.name, size: file.size, content: file.text }],
      title: file.name,
      content: file.text,
      syntax: EXTENSION_MAP[ext] || 'plaintext',
    }
  }

  const combined = results
    .map((f) => `// --- ${f.name} ---\n${f.text}`)
    .join('\n\n')
  return {
    files: results.map((r) => ({
      name: r.name,
      size: r.size,
      content: r.text,
    })),
    title: `Upload: ${results.length} files`,
    content: combined,
    syntax: 'plaintext',
  }
}

export interface ParsedPasteContent {
  text: string | null
  files: UploadedFile[]
}

// ponytail: splits paste into main text (if any) and attached files (if any)
export function parsePasteContent(
  content: string,
  explicitFiles?: UploadedFile[],
): ParsedPasteContent {
  if (explicitFiles && explicitFiles.length > 0) {
    return {
      text: content.trim() || null,
      files: explicitFiles,
    }
  }

  const fileHeaderRegex = /^\/\/\s*---\s*(.+?)\s*---\s*$/gm
  const matches = Array.from(content.matchAll(fileHeaderRegex))

  if (matches.length === 0) {
    return {
      text: content,
      files: [],
    }
  }

  const firstHeaderIndex = matches[0].index ?? 0
  const leadingText = content.slice(0, firstHeaderIndex).trim()

  const files: UploadedFile[] = []
  for (let i = 0; i < matches.length; i++) {
    const match = matches[i]
    const fileName = match[1].trim()
    const startIndex = (match.index ?? 0) + match[0].length + 1
    const endIndex =
      i + 1 < matches.length
        ? (matches[i + 1].index ?? content.length)
        : content.length

    const fileContent = content.slice(startIndex, endIndex).trim()
    files.push({
      name: fileName,
      size: new Blob([fileContent]).size,
      content: fileContent,
    })
  }

  return {
    text: leadingText || null,
    files,
  }
}

// ponytail: extracts individual files from multi-file content or returns explicit files array
export function parsePasteFiles(
  content: string,
  defaultTitle?: string,
  explicitFiles?: UploadedFile[],
): UploadedFile[] {
  const parsed = parsePasteContent(content, explicitFiles)
  if (parsed.files.length > 0) {
    return parsed.files
  }
  return [
    {
      name: defaultTitle || 'paste.txt',
      size: new Blob([content]).size,
      content,
    },
  ]
}
