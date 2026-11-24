# Product Requirements: Markdown Content Management

## Overview

`cms` is a reusable Spring module for editable website pages and content
blocks. It can be included by `usma` and other Spring Boot applications.

Content is stored as ServiceBuilder entities defined by
`cms/src/main/resources/service.xml`. Markdown is rendered server-side for
Thymeleaf views. Binary assets remain owned by the existing Document Library.

## Goals

- Provide reusable page and block content management.
- Support `DRAFT`, `PUBLISHED`, and `ARCHIVED` lifecycle states.
- Keep draft edits isolated from published content.
- Render sanitized Markdown server-side through Thymeleaf.
- Reuse the Document Library as the sole source of truth for files and images.
- Support configurable caching, security, and host integration.

## Non-goals

- A separate binary storage system.
- Copying Document Library files into CMS storage.
- Multi-language or multi-stage approval workflows in v1.

## Users and permissions

| User | Role | Capabilities |
| --- | --- | --- |
| Site visitor | Anonymous/public | View published pages and referenced assets. |
| Content editor | `ROLE_CONTENT_EDITOR` | Create drafts, edit blocks, preview, publish, and archive. |
| Administrator | `ROLE_ADMIN` | All content editor capabilities. |

## Functional requirements

### Pages and blocks

Pages have a unique URL-safe slug, title, summary, status, version,
publication timestamp, and audit fields. Each page contains ordered blocks that
can be added, edited, reordered, hidden, or removed.

Initial block types are `MARKDOWN`, `RICH_TEXT`, `HERO`, `IMAGE_BANNER`,
`DOCUMENT_LIST`, and `CALLOUT`.

### Markdown rendering

Markdown blocks are rendered on the server before being passed to Thymeleaf.
The module should provide a renderer usable through a Thymeleaf helper,
attribute, or dialect processor, for example:

```html
<section th:utext="${@contentRenderer.markdown(block.body)}"></section>
```

The renderer must sanitize generated HTML with an allowlist. Raw unsanitized
author input must never be emitted with `th:utext`.

### Document Library references

Blocks may reference a primary document and secondary documents by the Document
Library's `Long` document ID. Because the CMS and Document Library run in the
same Spring Boot application, the default integration uses the generated
Document Library service layer directly.

The CMS must not copy or own binary files. Invalid references fail during
authoring or publishing. A reference deleted after publication must degrade
gracefully during public rendering rather than causing a 500 response.

### Lifecycle

- Saving a draft must not change the published page.
- Editors can preview drafts using the public rendering model.
- Publishing validates the draft, increments the version, records `publishedAt`,
  and invalidates public cache entries.
- Archiving removes the page from public resolution and invalidates its cache.

### Host integration

The module must not depend on `usma` classes. Host applications configure
security, database settings, URL prefixes, cache providers, and view names.

## Acceptance criteria

- A host can include the CMS module and generate its entities from the CMS
  ServiceBuilder definition.
- An unpublished draft is not publicly resolvable.
- A published page renders Markdown as sanitized server-side HTML.
- Editing a draft does not alter the current public page.
- Valid Document Library IDs resolve through the external API adapter.
- Invalid IDs fail validation during draft save or publication.
- Deleted published assets do not break public rendering.
- Unauthenticated users cannot access authoring endpoints.
