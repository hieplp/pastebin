# user-web

SPA to create and view pastes. Proxies `/api` to `pastebin-service` (`:9000`).

React 19 · Vite · TypeScript · Tailwind 4 · Zustand · Ky. Alias `@` → `src/`.

No React Router. `src/lib/router.ts` is `history` + `popstate` + click intercept. `App.tsx` picks the page from the path.

---

## How the code is split

```
pages/          screens (own their layout and submit/fetch)
components/     shared chrome and primitives
stores/         client state (one file per concern)
api/            HTTP (one file per resource)
hooks/          reusable browser behavior, no business rules
types/          shapes shared across the above
utils/          pure helpers
lib/            one-off platform bits (router)
```

A page may have a private `components/` folder. If a widget is used on more than one page, it moves to `components/ui` (or `layout` / `icons`).

---

## Project structure

```
user-web/
├── package.json
├── vite.config.ts                   /api → :9000
├── index.html
├── public/
└── src/
    ├── App.tsx                      `/` create, `/pastes/:id` and `/p/:id` detail
    ├── constants.ts                 syntax + expiry option lists
    ├── lib/                         history router
    ├── pages/
    │   ├── CreatePastePage/         index + editor/toolbar/actions/share card
    │   └── DetailPastePage/         index + toolbar/code/files/not-found
    ├── components/
    │   ├── layout/                  app shell, navbar
    │   ├── ui/                      input, select, copy/download, modal, alert
    │   └── icons/
    ├── stores/                      paste, theme, alert
    ├── api/                         pastes, files, Ky client
    ├── hooks/                       upload, drag-drop, clipboard, shortcuts
    ├── types/
    └── utils/
```

Each page folder is `index.tsx` (logic) + `components/` (pieces only that page needs).

---

## Naming

| Kind | Pattern | Example |
|---|---|---|
| Screen folder | `{Verb}{Noun}Page` | `CreatePastePage`, `DetailPastePage` |
| Page-private widget | role in that screen | `EditorToolbar`, `DetailCodeArea`, `CreatedPasteCard` |
| Shared primitive | what it is | `CopyButton`, `ThemeToggle` |
| Store | `{concern}.ts` → `use{Concern}Store` | `paste.ts` → `usePasteStore` |
| API module | `{resource}.ts` → `{resource}Api` | `pastes.ts` → `pasteApi` |
| Hook | `use{Thing}.ts` | `useFileUpload`, `useClickOutside` |
| Type file | `{concept}.ts` | `types/paste.ts` (`DraftPaste`, `Paste`) |

Page-private names stay prefixed with the screen’s idea (`Editor*`, `Detail*`) so they don’t collide with `components/ui`. Don’t put fetch/submit in a widget — that’s the page + store.

Barrels (`index.ts`) re-export the folder. Import from `@/pages`, `@/stores`, `@/api`.

---

## Adding something

Want a “my pastes” list?

1. `pages/ListPastesPage/` with `index.tsx` (+ private components if the table is fat)
2. A branch in `App.tsx` (and a matcher in `lib/router.ts` if the path is more than `/`)
3. `pasteApi.listOwn` in `api/pastes.ts` (the client already has a stub)
4. Actions on `usePasteStore` — don’t add a second paste store

Want a new control used on create **and** detail? `components/ui/{Name}.tsx`. Used once? Keep it under that page’s `components/`.

---

## What the UI actually does

**Create** (`/`) — toolbar (title, syntax, expiry, upload), editor, attachment chips, submit. Success swaps the editor for a share card (`/pastes/{alias or id}`). Expiry select (`never` / `10m` / `1h` / `1d` / `1w` / `burn`) is turned into `expiredAt` + `burnAfterRead` in the page.

**Detail** (`/pastes/:id`) — fetch paste, line-gutter or raw, copy/download, attachment tabs. Missing/expired → not-found screen.

**Data** — UI → store → `pasteApi` / `fileApi` → Ky `/api` → Vite proxy → `:9000`. The client unwraps `{ data }` so stores never see the envelope. Create is `FormData`: JSON part `request` (enum names uppercased) + `files`.

**Stores** — `paste` (draft persisted), `theme` (light/dark/system, matches the FOUC script in `index.html`), `alert` (success auto-clears).

---

## Scripts

```bash
bun install && bun run dev     # :5173, /api → :9000
bun run build
bun run lint                   # oxlint
```
