import { describe, it, expect, afterEach } from 'bun:test'
import { copyToClipboard, copyFallback } from './useClipboard'

describe('copyToClipboard and fallback', () => {
  const originalNavigator = globalThis.navigator
  const originalDocument = globalThis.document

  afterEach(() => {
    Object.defineProperty(globalThis, 'navigator', {
      value: originalNavigator,
      writable: true,
      configurable: true,
    })
    Object.defineProperty(globalThis, 'document', {
      value: originalDocument,
      writable: true,
      configurable: true,
    })
  })

  it('copies with navigator.clipboard.writeText when available', async () => {
    let writtenText = ''
    Object.defineProperty(globalThis, 'navigator', {
      value: {
        clipboard: {
          writeText: async (t: string) => {
            writtenText = t
          },
        },
      },
      writable: true,
      configurable: true,
    })

    const success = await copyToClipboard('https://example.com/pastes/p_123')
    expect(success).toBe(true)
    expect(writtenText).toBe('https://example.com/pastes/p_123')
  })

  it('falls back to execCommand when navigator.clipboard.writeText throws', async () => {
    Object.defineProperty(globalThis, 'navigator', {
      value: {
        clipboard: {
          writeText: async () => {
            throw new Error('NotAllowedError: Document is not focused')
          },
        },
      },
      writable: true,
      configurable: true,
    })

    let command = ''
    let appendedValue = ''
    const mockDocument = {
      createElement: () => ({
        set value(val: string) {
          appendedValue = val
        },
        style: {},
        setAttribute: () => {},
        focus: () => {},
        select: () => {},
      }),
      body: {
        appendChild: () => {},
        removeChild: () => {},
      },
      execCommand: (cmd: string) => {
        command = cmd
        return true
      },
    }

    Object.defineProperty(globalThis, 'document', {
      value: mockDocument,
      writable: true,
      configurable: true,
    })

    const success = await copyToClipboard('fallback-url-1')
    expect(success).toBe(true)
    expect(command).toBe('copy')
    expect(appendedValue).toBe('fallback-url-1')
  })

  it('falls back to execCommand when navigator.clipboard is undefined (insecure context)', async () => {
    Object.defineProperty(globalThis, 'navigator', {
      value: {},
      writable: true,
      configurable: true,
    })

    let command = ''
    const mockDocument = {
      createElement: () => ({
        set value(_val: string) {},
        style: {},
        setAttribute: () => {},
        focus: () => {},
        select: () => {},
      }),
      body: {
        appendChild: () => {},
        removeChild: () => {},
      },
      execCommand: (cmd: string) => {
        command = cmd
        return true
      },
    }

    Object.defineProperty(globalThis, 'document', {
      value: mockDocument,
      writable: true,
      configurable: true,
    })

    const success = await copyToClipboard('fallback-url-2')
    expect(success).toBe(true)
    expect(command).toBe('copy')
  })

  it('returns false when neither method works', async () => {
    Object.defineProperty(globalThis, 'navigator', {
      value: {},
      writable: true,
      configurable: true,
    })

    const mockDocument = {
      createElement: () => ({
        style: {},
        setAttribute: () => {},
        focus: () => {},
        select: () => {},
      }),
      body: {
        appendChild: () => {},
        removeChild: () => {},
      },
      execCommand: () => false,
    }

    Object.defineProperty(globalThis, 'document', {
      value: mockDocument,
      writable: true,
      configurable: true,
    })

    const success = await copyFallback('failed-url')
    expect(success).toBe(false)
  })
})
