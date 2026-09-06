import type { SelectOption } from '@/types'

export const EXTENSION_MAP: Record<string, string> = {
  js: 'javascript',
  jsx: 'javascript',
  ts: 'typescript',
  tsx: 'typescript',
  py: 'python',
  json: 'json',
  md: 'markdown',
  html: 'html',
  css: 'css',
  sql: 'sql',
  rs: 'rust',
  go: 'go',
  sh: 'bash',
  bash: 'bash',
  txt: 'plaintext',
}

export const SYNTAX_TO_EXT: Record<string, string> = {
  javascript: 'js',
  typescript: 'ts',
  python: 'py',
  json: 'json',
  markdown: 'md',
  html: 'html',
  css: 'css',
  sql: 'sql',
  rust: 'rs',
  go: 'go',
  bash: 'sh',
  plaintext: 'txt',
}

export const SYNTAX_OPTIONS: SelectOption[] = [
  { value: 'plaintext', label: 'Plain Text' },
  { value: 'javascript', label: 'JavaScript' },
  { value: 'typescript', label: 'TypeScript' },
  { value: 'python', label: 'Python' },
  { value: 'json', label: 'JSON' },
  { value: 'html', label: 'HTML' },
  { value: 'css', label: 'CSS' },
  { value: 'markdown', label: 'Markdown' },
  { value: 'sql', label: 'SQL' },
  { value: 'rust', label: 'Rust' },
  { value: 'go', label: 'Go' },
  { value: 'bash', label: 'Bash / Shell' },
]

export const EXPIRATION_OPTIONS: SelectOption[] = [
  { value: 'never', label: 'Never expire' },
  { value: '10m', label: '10 Minutes' },
  { value: '1h', label: '1 Hour' },
  { value: '1d', label: '1 Day' },
  { value: '1w', label: '1 Week' },
  { value: 'burn', label: 'Burn after read' },
]
