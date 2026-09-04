import { describe, it, expect } from 'bun:test'
import { matchPasteRoute } from './router'
import type { PasteItem } from '../api/types'

describe('router and paste redirection', () => {
  it('matches /pastes/:id route', () => {
    const route = matchPasteRoute('/pastes/p_12345')
    expect(route).not.toBeNull()
    expect(route?.pasteId).toBe('p_12345')
  })

  it('matches /pastes/:id with trailing slash', () => {
    const route = matchPasteRoute('/pastes/my-alias/')
    expect(route).not.toBeNull()
    expect(route?.pasteId).toBe('my-alias')
  })

  it('matches short link /p/:id route', () => {
    const route = matchPasteRoute('/p/abcde')
    expect(route).not.toBeNull()
    expect(route?.pasteId).toBe('abcde')
  })

  it('decodes URI encoded paste IDs or aliases', () => {
    const route = matchPasteRoute('/pastes/my%20note')
    expect(route).not.toBeNull()
    expect(route?.pasteId).toBe('my note')
  })

  it('does not match root or other routes', () => {
    expect(matchPasteRoute('/')).toBeNull()
    expect(matchPasteRoute('/create')).toBeNull()
    expect(matchPasteRoute('/pastes')).toBeNull()
    expect(matchPasteRoute('/pastes/p_123/edit')).toBeNull()
  })

  it('formats redirect target based on alias or pasteId', () => {
    const pasteWithAlias: PasteItem = {
      pasteId: 'p_987',
      alias: 'custom-slug',
      title: 'Test',
      content: 'hello',
    }
    const targetWithAlias = `/pastes/${pasteWithAlias.alias || pasteWithAlias.pasteId}?created=true`
    expect(targetWithAlias).toBe('/pastes/custom-slug?created=true')

    const pasteWithoutAlias: PasteItem = {
      pasteId: 'p_987',
      title: 'Test',
      content: 'hello',
    }
    const targetWithoutAlias = `/pastes/${pasteWithoutAlias.alias || pasteWithoutAlias.pasteId}?created=true`
    expect(targetWithoutAlias).toBe('/pastes/p_987?created=true')
  })
})
