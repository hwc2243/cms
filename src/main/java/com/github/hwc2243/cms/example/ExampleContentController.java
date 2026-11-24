package com.github.hwc2243.cms.example;

import com.github.hwc2243.cms.dto.ContentBlockDTO;
import com.github.hwc2243.cms.dto.ContentPageDTO;
import com.github.hwc2243.cms.model.ContentBlockContentState;
import com.github.hwc2243.cms.service.ContentBlockService;
import com.github.hwc2243.cms.service.ContentManagementService;
import com.github.hwc2243.cms.service.RenderedContentPage;
import com.github.hwc2243.cms.service.ContentPageService;
import com.github.hwc2243.cms.service.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class ExampleContentController {

  private final ContentManagementService contentManagementService;
  private final ContentBlockService blockService;
  private final ContentPageService pageService;

  public ExampleContentController(
      ContentManagementService contentManagementService,
      ContentBlockService blockService,
      ContentPageService pageService) {
    this.contentManagementService = contentManagementService;
    this.blockService = blockService;
    this.pageService = pageService;
  }

  @GetMapping("/")
  public String home(Model model) {
    try {
      RenderedContentPage page = contentManagementService.getPublishedPage("welcome");
      if (page == null) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Example page not found");
      }
      model.addAttribute("page", page);
      model.addAttribute("draftPreview", false);
      return "example/index";
    } catch (ServiceException ex) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
          "Unable to load example page", ex);
    }
  }

  @GetMapping("/edit")
  public String edit(@RequestParam("blockId") Long blockId, Model model) {
    try {
      ContentBlockDTO block = blockService.get(blockId);
      if (block == null) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Example block not found");
      }
      if (block.getContentState() == ContentBlockContentState.PUBLISHED) {
        Long pageId = block.getPageId();
        Integer sortOrder = block.getSortOrder();
        block = blockService.findAll().stream()
            .filter(candidate -> candidate.getContentState() == ContentBlockContentState.DRAFT)
            .filter(candidate -> java.util.Objects.equals(candidate.getPageId(), pageId))
            .filter(candidate -> java.util.Objects.equals(candidate.getSortOrder(), sortOrder))
            .findFirst()
            .orElse(block);
      }
      ContentPageDTO page = pageService.get(block.getPageId());
      if (page == null) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Example page not found");
      }
      model.addAttribute("page", page);
      model.addAttribute("block", block);
      return "example/edit";
    } catch (ServiceException ex) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
          "Unable to load example block", ex);
    }
  }

  @GetMapping("/preview")
  public String preview(@RequestParam("blockId") Long blockId, Model model) {
    try {
      ContentBlockDTO block = blockService.get(blockId);
      if (block == null) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Example block not found");
      }
      if (block.getContentState() == ContentBlockContentState.PUBLISHED) {
        Long pageId = block.getPageId();
        Integer sortOrder = block.getSortOrder();
        boolean hasDraft = blockService.findAll().stream()
            .anyMatch(candidate -> candidate.getContentState() == ContentBlockContentState.DRAFT
                && java.util.Objects.equals(candidate.getPageId(), pageId)
                && java.util.Objects.equals(candidate.getSortOrder(), sortOrder));
        if (!hasDraft) {
          throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No unpublished draft exists");
        }
      }
      RenderedContentPage page = contentManagementService.previewPage(block.getPageId());
      model.addAttribute("page", page);
      model.addAttribute("draftPreview", true);
      return "example/index";
    } catch (ServiceException ex) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
          "Unable to load draft preview", ex);
    }
  }

  private Long requiredPageId() throws ServiceException {
    var page = contentManagementService.getPublishedPage("welcome");
    if (page == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Example page not found");
    }
    return page.id();
  }
}
