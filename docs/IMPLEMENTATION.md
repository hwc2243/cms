# Implementation Plan

This plan implements the reusable `cms` Spring module described in the PRD and
technical design. The module must not depend on `usma` application classes.

## Phase 1: Module and ServiceBuilder setup

- Establish `cms` as a standalone Maven Spring module.
- Add the ServiceBuilder Maven plugin using the module's configured version.
- Create `cms/src/main/resources/service.xml`.
- Define `contentPage` and `contentBlock` entities, including relationships,
  status, ordering, Markdown body, document IDs, and audit data.
- Confirm generated packages and host extension points.

Generated entities and services are the persistence API. Do not create parallel
hand-written JPA entities for the same data.

## Phase 2: Content model

Define `ContentStatus` (`DRAFT`, `PUBLISHED`, `ARCHIVED`), block types
(`MARKDOWN`, `RICH_TEXT`, `HERO`, `IMAGE_BANNER`, `DOCUMENT_LIST`, `CALLOUT`),
page metadata, audit fields, and ordered blocks with visibility, body, metadata,
and `Long` Document Library references.

The model must match the finders and services generated from `service.xml`.

## Phase 3: Document Library integration

Create a replaceable `DocumentReferenceResolver`. Its default implementation
uses the generated Document Library service layer directly because both modules
run in the same Spring Boot application. It must validate IDs, resolve
filename/MIME type/public URL, avoid copying binary content, and omit/log
missing assets during public rendering.

Keep a remote/HTTP implementation as a possible future adapter, but do not make
REST part of the initial CMS-to-Document-Library contract.

Keep external response DTOs separate from the CMS content model.

## Phase 4: Markdown rendering and sanitization

Create a `ContentRenderer` that converts Markdown to HTML with a
CommonMark-compatible renderer, sanitizes the result with an explicit allowlist,
and returns safe HTML for Thymeleaf rendering. Expose it through a Thymeleaf
dialect, attribute processor, or host-registered helper. Rich-text blocks use
the same sanitization boundary.

## Phase 5: Content services and lifecycle

Implement a `ContentManagementService` on generated ServiceBuilder services.
It supports draft creation/update, preview, published lookup, publication,
archival, versioning, audit data, and cache eviction. Draft edits must not alter
published blocks. Publication validates and sanitizes before replacing the
published block set transactionally.

## Phase 6: Web integration

Provide configurable public and authoring controllers or controller components.
The conventional public API is `GET /api/content/{slug}`. Admin prefixes,
security configuration, and view names are host-configurable. Admin routes
require `ROLE_ADMIN` or `ROLE_CONTENT_EDITOR`.

## Phase 7: Verification

Add tests for ServiceBuilder generation and host integration, draft isolation,
publication, Markdown rendering, XSS sanitization, Document Library validation,
missing-asset degradation, cache invalidation, and configurable security and
route prefixes.
