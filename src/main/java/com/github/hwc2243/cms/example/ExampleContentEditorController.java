package com.github.hwc2243.cms.example;

import com.github.hwc2243.cms.dto.ContentBlockDTO;
import com.github.hwc2243.cms.model.ContentBlockContentState;
import com.github.hwc2243.cms.service.ContentBlockService;
import com.github.hwc2243.cms.service.ContentManagementService;
import com.github.hwc2243.cms.service.ServiceException;
import java.util.Objects;
import org.springframework.stereotype.Controller;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ExampleContentEditorController {

  private final ContentBlockService blockService;
  private final ContentManagementService contentManagementService;

  public ExampleContentEditorController(
      ContentBlockService blockService,
      ContentManagementService contentManagementService) {
    this.blockService = blockService;
    this.contentManagementService = contentManagementService;
  }

  @PostMapping("/edit")
  @CacheEvict(cacheNames = "publishedContent", key = "'welcome'")
  public String saveDraft(
      @RequestParam("blockId") Long blockId,
      @RequestParam("body") String body,
      RedirectAttributes attributes) throws ServiceException {
    ContentBlockDTO block = blockService.get(blockId);
    if (block == null) {
      throw new IllegalArgumentException("Example block not found: " + blockId);
    }
    ContentBlockDTO draft = block;
    if (block.getContentState() == ContentBlockContentState.PUBLISHED) {
      draft = new ContentBlockDTO();
      draft.setPageId(block.getPageId());
      draft.setSortOrder(block.getSortOrder());
      draft.setBlockType(block.getBlockType());
      draft.setHeading(block.getHeading());
      draft.setPrimaryDocumentId(block.getPrimaryDocumentId());
      draft.setSecondaryDocumentIds(block.getSecondaryDocumentIds());
      draft.setMetadataJson(block.getMetadataJson());
      draft.setVisible(block.getVisible());
      draft.setContentState(ContentBlockContentState.DRAFT);
    }
    draft.setBody(body);
    if (draft.getId() == null) {
      blockService.create(draft);
    } else {
      blockService.update(draft);
    }
    attributes.addFlashAttribute("message", "Draft saved. Publish to make it live.");
    return "redirect:/";
  }

  @PostMapping("/publish")
  public String publish(RedirectAttributes attributes) throws ServiceException {
    var page = contentManagementService.getPublishedPage("welcome");
    if (page == null) {
      throw new IllegalArgumentException("Example page not found");
    }
    contentManagementService.publishPage(page.id(), "example");
    attributes.addFlashAttribute("message", "Draft published.");
    return "redirect:/";
  }

  @PostMapping("/draft/delete")
  @CacheEvict(cacheNames = "publishedContent", key = "'welcome'")
  public String deleteDraft(
      @RequestParam("draftId") Long draftId,
      RedirectAttributes attributes) throws ServiceException {
    ContentBlockDTO draft = blockService.get(draftId);
    if (draft != null && draft.getContentState() == ContentBlockContentState.DRAFT) {
      blockService.delete(draftId);
      attributes.addFlashAttribute("message", "Draft deleted.");
    } else {
      // A stale preview must never be allowed to delete published content.
      attributes.addFlashAttribute("message", "No unpublished draft was deleted.");
    }
    return "redirect:/";
  }
}
