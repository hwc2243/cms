# CMS

Reusable Spring Boot content management module with ServiceBuilder persistence,
server-side Markdown rendering, and in-process Document Library integration.

## Run the standalone example

The module includes a small H2-backed application that seeds and renders a
published Markdown page.

```bash
cd cms
mvn spring-boot:run
```

Open <http://localhost:8080/> to view the rendered page.

Move the mouse over the rendered content to reveal the pencil icon. Select it
to edit the Markdown source. **Save draft** stores the edit without changing
the published page; **Publish draft** makes it live.

The executable artifact is also produced as:

```text
target/cms-0.0.1-SNAPSHOT-exec.jar
```

## Use the module in a host application

Add the CMS dependency and import the generated configuration/components from
the `com.github.hwc2243.cms` package. The host application should provide its
own database, security, cache configuration, and public asset URL provider.

Create and publish content through `ContentManagementService`:

```java
ContentDraftRequest request = new ContentDraftRequest(
    "about",
    "About",
    "About this site",
    List.of(new ContentBlockInput(
        0,
        ContentBlockBlockType.MARKDOWN,
        null,
        "# About\n\nThis is Markdown content.",
        null,
        null,
        null,
        true)));

ContentPageDTO page = contentManagementService.createDraft(request, actor);
contentManagementService.publishPage(page.getId(), actor);
```

Published content can be rendered in a Thymeleaf template using the safe
renderer output:

```html
<section th:utext="${@contentRenderer.markdown(block.body)}"></section>
```

The CMS uses the in-process `DocumentFileService` by default. Hosts can provide
a `DocumentAssetUrlProvider` bean to control the public URL generated for a
Document Library file.
