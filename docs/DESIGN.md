# Technical Design: Reusable Markdown CMS

## Module boundary

`cms` is a reusable Spring module that can be included in multiple Spring Boot
applications. It owns content persistence, lifecycle behavior, Markdown
rendering, sanitization, and integration abstractions. The host owns security,
database configuration, URL composition, and deployment.

The module must not import `usma` classes or assume a host package.

## ServiceBuilder model

CMS persistence is defined in:

```text
cms/src/main/resources/service.xml
```

The initial entities are `contentPage` and `contentBlock`.

`contentPage` contains a unique slug, title, summary, lifecycle status,
publication version and timestamp, and creation/update audit fields.

`contentBlock` contains its related page, order, block type, heading, body,
document references, metadata, and visibility. Document references use the
Document Library's `Long` IDs.

The ServiceBuilder definition is the persistence source of truth. Generated
files must not be hand-edited.

## Draft and publication representation

Draft and published content are represented by ServiceBuilder entities. Draft
blocks must be isolated from published blocks so an editor can save changes
without affecting visitors.

Publication validates and sanitizes the draft, replaces the published block set
transactionally, increments the page version, and clears the public cache. If
the generated model needs an explicit block-state field or separate draft
relation to express this distinction, that should be added to `service.xml`
rather than serializing the complete draft as an opaque snapshot.

## Document Library bridge

The CMS stores document identifiers and display metadata only; it does not store
binary files. Because the CMS and Document Library run in the same Spring Boot
application, the default resolver calls the generated Document Library service
layer directly, such as `DocumentFileService`.

Its conceptual API is:

```java
void validateDocumentIds(Collection<Long> ids);
Map<Long, AssetReference> resolveAssets(Collection<Long> ids);
```

`AssetReference` contains the document ID, filename, MIME type, and public URL.
The resolver should translate Document Library DTOs into this CMS-specific
model. The host application remains responsible for deciding how a file is
served publicly and for constructing its URL.

The resolver should remain an interface so a future remote adapter can be added,
but REST is not required for the initial integration.

Invalid references fail draft validation or publication. A reference deleted
after publication is logged and omitted from public output.

## Markdown and Thymeleaf rendering

The rendering pipeline is:

```text
Markdown source -> CommonMark renderer -> HTML allowlist sanitizer
              -> Thymeleaf model/view -> server-rendered response
```

The CMS exposes a `ContentRenderer` Spring bean for Thymeleaf expressions. The
renderer performs sanitization internally so callers do not need a separate
safety step:

```html
<section th:utext="${@contentRenderer.markdown(block.body)}"></section>
```

Rich-text blocks use the same sanitizer. URLs, links, images, and allowed HTML
elements must be restricted by the sanitizer policy.

## Services and lifecycle operations

The CMS service layer provides draft creation and update, page listing and
inspection, preview, published lookup by slug, publication, and archival.
Public lookup returns only `PUBLISHED` pages.

## Web and host integration

The module provides configurable controller components for public content,
authoring operations, and Thymeleaf page rendering. The conventional public
endpoint is `GET /api/content/{slug}`; admin prefixes are host-configurable.

Admin operations require `ROLE_ADMIN` or `ROLE_CONTENT_EDITOR`.

## Caching and resilience

Published-page reads may be cached by slug. Publication and archival evict the
affected slug immediately. Cache enablement and provider choice remain host
responsibilities.

Missing pages and archived pages resolve as not found. Missing published assets
must not cause a public 500 response.
