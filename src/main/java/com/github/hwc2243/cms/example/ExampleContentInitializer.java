package com.github.hwc2243.cms.example;

import com.github.hwc2243.cms.service.ContentBlockInput;
import com.github.hwc2243.cms.service.ContentDraftRequest;
import com.github.hwc2243.cms.service.ContentManagementService;
import com.github.hwc2243.cms.service.ServiceException;
import com.github.hwc2243.cms.model.ContentBlockBlockType;
import com.github.hwc2243.cms.service.ContentPageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ExampleContentInitializer implements CommandLineRunner {

  private final ContentPageService pageService;
  private final ContentManagementService contentManagementService;

  public ExampleContentInitializer(
      ContentPageService pageService,
      ContentManagementService contentManagementService) {
    this.pageService = pageService;
    this.contentManagementService = contentManagementService;
  }

  @Override
  public void run(String... args) throws ServiceException {
    if (pageService.fetchBySlug("welcome") != null) {
      return;
    }

    ContentDraftRequest request = new ContentDraftRequest(
        "welcome",
        "CMS Example",
        "A standalone example of the reusable CMS module.",
        java.util.List.of(new ContentBlockInput(
            0,
            ContentBlockBlockType.MARKDOWN,
            "Welcome",
            "# Welcome\n\nThis page is rendered from **Markdown** by the CMS module.\n\n- ServiceBuilder persistence\n- Server-side rendering\n- Sanitized HTML output",
            null,
            null,
            null,
            true)));

    var page = contentManagementService.createDraft(request, "example");
    contentManagementService.publishPage(page.getId(), "example");
  }
}
