# pastebin-service

API for creating and fetching pastes (text + optional attachments).

Java 25 · Spring Boot 4 · JPA or Mongo · hexagonal. Listens on **`:9000`**. Docs at `/swagger-ui` and `/scalar`.

---

## How the code is split

Three rings. Outer rings know about Spring, HTTP, JPA/Mongo, S3. The middle ring is use cases. The inner ring is plain Java.

```
adapter/in     HTTP, cron          how the world calls us
application    ports + services    what the app does
domain         models, VOs         rules, no framework
adapter/out    JPA or Mongo, disk, S3
```


A request never jumps rings. Controller → use case → domain / outbound port → adapter.

---

## Project structure

```
pastebin-service/
├── build.gradle.kts
├── run.sh
├── src/main/java/dev/hieplp/pastebin/
│   ├── adapter/
│   │   ├── in/
│   │   │   ├── web/                 controller, payload, mapper, security, errors
│   │   │   └── schedule/            expired pastes + orphan files
│   │   └── out/
│   │       ├── jpa/                 entity, repository, mapper, adapter (default)
│   │       ├── mongo/               document, repository, mapper, adapter
│   │       ├── localstorage/        disk (default)
│   │       └── s3/                  MinIO / S3
│   ├── application/
│   │   ├── port/
│   │   │   ├── in/{paste,file}/     use-case interfaces
│   │   │   └── out/{paste,file,storage}/
│   │   ├── service/{paste,file}/    one class per use case
│   │   └── dto/{paste,file}/        command, query, result
│   └── domain/
│       ├── model/                   Paste, PasteFile
│       ├── vo/                      PasteId, Alias, StorageKey, …
│       ├── enums/                   Syntax, Privacy, PasteStatus
│       └── exception/
├── src/main/resources/
│   ├── application.yaml
│   └── db/migration/
└── src/test/java/…                  same packages as production
```

A request never jumps rings: controller → use case → domain / outbound port → adapter.
---

## Naming

If you know the verb and the aggregate, you can guess the file.

| Kind | Pattern | Example |
|---|---|---|
| Use case | `{Verb}{Thing}UseCase` | `CreatePasteUseCase`, `DownloadFileUseCase` |
| Implementation | `{Verb}{Thing}Service` | `CreatePasteService` |
| Outbound port | `{Verb}{Thing}Port` | `SavePastePort`, `UploadStoragePort` |
| Driven adapter | `{Thing}Adapter` or `{Tech}StorageAdapter` | `PasteAdapter`, `S3StorageAdapter` |
| HTTP | `{Thing}Controller` | `PasteController` |
| Wire payload | `{Verb}{Thing}Request` / `Response` | `CreatePasteRequest` |
| App DTO | `{Verb}{Thing}Command` / `Query` / `Result` | `GetPasteQuery`, `PasteResult` |
| Domain VO | concept name | `PasteId`, `Alias`, `StorageKey` |
| Enum | concept name | `Syntax`, `Privacy`, `PasteStatus` |

Split by **aggregate**, not by layer dump: `…/paste/` vs `…/file/` vs `…/storage/` repeats in ports, services, and DTOs.

Mappers sit next to the adapter that needs them (`adapter/in/web/mapper`, `adapter/out/jpa/mapper`). Payloads stay in `payload/{aggregate}/`. Config/properties sit in the adapter they configure.

`CommandEnvelope` wraps a command plus `Actor` (anonymous today). Mutations go through it; reads use a `*Query`.

---

## Adding something

Want “list my pastes”? You should not invent a new top-level folder.

1. `ListPastesUseCase` + `ListPastesService` under `paste`
2. `ListPastesQuery` / `ListPastesResult` under `dto/paste`
3. A method on `GetPastePort` + `PasteAdapter` if the query is new
4. A method on `PasteController` + a `*Response`

Want a new blob backend? Implement the four storage ports (`Upload`, `Read`, `Delete`, `List`) behind `@ConditionalOnProperty(pastebin.storage.type=…)`. Don’t touch use cases.

Want a new DB? Same idea: implement paste/file/root ports behind `@ConditionalOnProperty(pastebin.persistence.type=…)`.

Delete-paste already exists as a use case (cleanup uses it). There is no HTTP delete yet — that would be a controller method, not a new service.

---

## What the app actually does

**HTTP** — two resources: `/pastes` (create multipart, get by id or alias) and `/files/{id}` (metadata + download). Envelope `{ code, message, data }`. Public; CSRF off.

**Create** — reject empty body-and-no-files, reject duplicate alias, persist `Paste` (`ACTIVE`, default `PUBLIC`), then upload attachments and save `PasteFile` rows.

**Get** — lookup id or alias. Inactive / expired → 404 (expired is deactivated first). Burn-after-read deactivates on first successful read but still returns the body so a second tab fetch of the same URL can work.

**Cleanup** — hourly: delete expired/`INACTIVE` pastes (files then row). Weekly: delete storage objects whose key is not in `paste_files`.

**Persistence** — `jpa` (default, Postgres) or `mongo`. Same ports, one adapter active.

**Storage** — `local` (default, `uploads/`) or `s3` (MinIO at `:9002`). Same ports, one adapter active.

---

## Run

```bash
./run.sh                                    # postgres + bootRun on :9000
PASTEBIN_PERSISTENCE_TYPE=mongo ./run.sh    # mongo instead
./gradlew test
```
