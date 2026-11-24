package com.github.hwc2243.cms.service;

import com.github.hwc2243.cms.dto.ContentBlockDTO;
import com.github.hwc2243.cms.dto.ContentPageDTO;
import com.github.hwc2243.cms.model.ContentBlockBlockType;
import com.github.hwc2243.cms.model.ContentBlockContentState;
import com.github.hwc2243.cms.model.ContentPageStatus;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ContentManagementService {

  private static final Pattern SLUG_PATTERN = Pattern.compile("^[a-z0-9]+(?:-[a-z0-9]+)*$");

  private final ContentPageService pageService;
  private final ContentBlockService blockService;
  private final DocumentReferenceResolver documentResolver;
  private final ContentRenderer contentRenderer;

  public ContentManagementService(
      ContentPageService pageService,
      ContentBlockService blockService,
      DocumentReferenceResolver documentResolver,
      ContentRenderer contentRenderer) {
    this.pageService = pageService;
    this.blockService = blockService;
    this.documentResolver = documentResolver;
    this.contentRenderer = contentRenderer;
  }

  @Transactional
  public ContentPageDTO createDraft(ContentDraftRequest request, String actor)
      throws ServiceException {
    validateRequest(request);
    if (pageService.fetchBySlug(request.slug()) != null) {
      throw new ServiceException("A content page already exists for slug: " + request.slug());
    }

    validateDocuments(request.blocks());
    LocalDateTime now = LocalDateTime.now();
    ContentPageDTO page = pageFromRequest(request);
    page.setStatus(ContentPageStatus.DRAFT);
    page.setVersion(1);
    page.setCreatedAt(now);
    page.setUpdatedAt(now);
    page.setCreatedBy(actor);
    page.setUpdatedBy(actor);
    ContentPageDTO saved = pageService.create(page);
    replaceBlocks(saved.getId(), request.blocks(), ContentBlockContentState.DRAFT);
    return saved;
  }

  @Transactional
  public ContentPageDTO updateDraft(Long pageId, ContentDraftRequest request, String actor)
      throws ServiceException {
    validateRequest(request);
    ContentPageDTO page = requiredPage(pageId);
    ContentPageDTO slugOwner = pageService.fetchBySlug(request.slug());
    if (slugOwner != null && !Objects.equals(slugOwner.getId(), pageId)) {
      throw new ServiceException("A content page already exists for slug: " + request.slug());
    }

    validateDocuments(request.blocks());
    page.setSlug(request.slug());
    page.setTitle(request.title());
    page.setSummary(request.summary());
    page.setUpdatedAt(LocalDateTime.now());
    page.setUpdatedBy(actor);
    ContentPageDTO saved = pageService.update(page);
    deleteBlocks(pageId, ContentBlockContentState.DRAFT);
    replaceBlocks(pageId, request.blocks(), ContentBlockContentState.DRAFT);
    return saved;
  }

  @Transactional(readOnly = true)
  public RenderedContentPage previewPage(Long pageId) throws ServiceException {
    ContentPageDTO page = requiredPage(pageId);
    List<ContentBlockDTO> blocks = previewBlocks(pageId);
    return renderPage(page, blocks);
  }

  @Cacheable(cacheNames = "publishedContent", key = "#slug")
  @Transactional(readOnly = true)
  public RenderedContentPage getPublishedPage(String slug) throws ServiceException {
    ContentPageDTO page = pageService.fetchBySlug(slug);
    if (page == null || page.getStatus() != ContentPageStatus.PUBLISHED) {
      return null;
    }
    return renderPage(page, blocksFor(page.getId(), ContentBlockContentState.PUBLISHED));
  }

  @Transactional
  @CacheEvict(cacheNames = "publishedContent", key = "#result.slug")
  public ContentPageDTO publishPage(Long pageId, String actor) throws ServiceException {
    ContentPageDTO page = requiredPage(pageId);
    List<ContentBlockDTO> drafts = blocksFor(pageId, ContentBlockContentState.DRAFT);
    if (drafts.isEmpty()) {
      throw new ServiceException("Cannot publish a page without draft blocks: " + pageId);
    }

    validateDocumentsFromDtos(drafts);
    List<ContentBlockDTO> publicationBlocks = previewBlocks(pageId);
    deleteBlocks(pageId, ContentBlockContentState.PUBLISHED);
    for (ContentBlockDTO block : publicationBlocks) {
      copyBlock(block, ContentBlockContentState.PUBLISHED);
    }
    deleteBlocks(pageId, ContentBlockContentState.DRAFT);

    page.setStatus(ContentPageStatus.PUBLISHED);
    page.setVersion(page.getVersion() == null ? 1 : page.getVersion() + 1);
    page.setPublishedAt(LocalDateTime.now());
    page.setUpdatedAt(LocalDateTime.now());
    page.setUpdatedBy(actor);
    return pageService.update(page);
  }

  @Transactional
  @CacheEvict(cacheNames = "publishedContent", key = "#result.slug")
  public ContentPageDTO archivePage(Long pageId, String actor) throws ServiceException {
    ContentPageDTO page = requiredPage(pageId);
    page.setStatus(ContentPageStatus.ARCHIVED);
    page.setUpdatedAt(LocalDateTime.now());
    page.setUpdatedBy(actor);
    return pageService.update(page);
  }

  private ContentPageDTO pageFromRequest(ContentDraftRequest request) {
    ContentPageDTO page = new ContentPageDTO();
    page.setSlug(request.slug());
    page.setTitle(request.title());
    page.setSummary(request.summary());
    return page;
  }

  private void replaceBlocks(Long pageId, List<ContentBlockInput> inputs,
      ContentBlockContentState state) throws ServiceException {
    List<ContentBlockInput> blocks = inputs == null ? List.of() : inputs;
    for (int index = 0; index < blocks.size(); index++) {
      ContentBlockInput input = blocks.get(index);
      ContentBlockDTO block = new ContentBlockDTO();
      block.setPageId(pageId);
      block.setSortOrder(input.sortOrder() == null ? index : input.sortOrder());
      block.setBlockType(input.blockType());
      block.setHeading(input.heading());
      block.setBody(input.body());
      block.setPrimaryDocumentId(input.primaryDocumentId());
      block.setSecondaryDocumentIds(serializeIds(input.secondaryDocumentIds()));
      block.setMetadataJson(input.metadataJson());
      block.setVisible(input.visible() == null || input.visible());
      block.setContentState(state);
      blockService.create(block);
    }
  }

  private void copyBlock(ContentBlockDTO source, ContentBlockContentState state)
      throws ServiceException {
    ContentBlockDTO block = new ContentBlockDTO();
    block.setPageId(source.getPageId());
    block.setSortOrder(source.getSortOrder());
    block.setBlockType(source.getBlockType());
    block.setHeading(source.getHeading());
    block.setBody(source.getBody());
    block.setPrimaryDocumentId(source.getPrimaryDocumentId());
    block.setSecondaryDocumentIds(source.getSecondaryDocumentIds());
    block.setMetadataJson(source.getMetadataJson());
    block.setVisible(source.getVisible());
    block.setContentState(state);
    blockService.create(block);
  }

  private List<ContentBlockDTO> blocksFor(Long pageId, ContentBlockContentState state)
      throws ServiceException {
    return blockService.findAll().stream()
        .filter(block -> Objects.equals(block.getPageId(), pageId))
        .filter(block -> block.getContentState() == state)
        .sorted(Comparator.comparing(ContentBlockDTO::getSortOrder,
            Comparator.nullsLast(Comparator.naturalOrder())))
        .toList();
  }

  private void deleteBlocks(Long pageId, ContentBlockContentState state)
      throws ServiceException {
    for (ContentBlockDTO block : blocksFor(pageId, state)) {
      blockService.delete(block.getId());
    }
  }

  private RenderedContentPage renderPage(ContentPageDTO page, List<ContentBlockDTO> blocks)
      throws ServiceException {
    Set<Long> ids = new LinkedHashSet<>();
    for (ContentBlockDTO block : blocks) {
      if (block.getPrimaryDocumentId() != null) {
        ids.add(block.getPrimaryDocumentId());
      }
      ids.addAll(parseIds(block.getSecondaryDocumentIds()));
    }
    Map<Long, AssetReference> assets = documentResolver.resolveAssets(ids);
    List<RenderedContentBlock> renderedBlocks = new ArrayList<>();
    List<ContentBlockDTO> draftBlocks = blocksFor(page.getId(), ContentBlockContentState.DRAFT);
    for (ContentBlockDTO block : blocks) {
      String html = block.getBlockType() == ContentBlockBlockType.MARKDOWN
          ? contentRenderer.markdown(block.getBody())
          : contentRenderer.richText(block.getBody());
      List<AssetReference> secondary = parseIds(block.getSecondaryDocumentIds()).stream()
          .map(assets::get)
          .filter(Objects::nonNull)
          .toList();
      ContentBlockDTO draft = block.getContentState() == ContentBlockContentState.PUBLISHED
          ? draftBlocks.stream()
              .filter(candidate -> Objects.equals(candidate.getSortOrder(), block.getSortOrder()))
              .findFirst()
              .orElse(null)
          : null;
      renderedBlocks.add(new RenderedContentBlock(
          block.getId(), block.getSortOrder(), block.getBlockType(), block.getHeading(),
          block.getBody(), html,
          assets.get(block.getPrimaryDocumentId()), secondary, block.getMetadataJson(),
          block.getVisible(), draft == null ? null : draft.getId(), draft != null,
          block.getContentState() == ContentBlockContentState.DRAFT));
    }
    return new RenderedContentPage(page.getId(), page.getSlug(), page.getTitle(), page.getSummary(),
        page.getStatus(), page.getVersion(), page.getPublishedAt(), renderedBlocks);
  }

  private List<ContentBlockDTO> previewBlocks(Long pageId) throws ServiceException {
    List<ContentBlockDTO> published = blocksFor(pageId, ContentBlockContentState.PUBLISHED);
    List<ContentBlockDTO> drafts = blocksFor(pageId, ContentBlockContentState.DRAFT);
    if (drafts.isEmpty()) {
      return published;
    }
    List<ContentBlockDTO> merged = new ArrayList<>();
    Set<Integer> draftOrders = drafts.stream()
        .map(ContentBlockDTO::getSortOrder)
        .collect(Collectors.toSet());
    for (ContentBlockDTO block : published) {
      ContentBlockDTO replacement = drafts.stream()
          .filter(candidate -> Objects.equals(candidate.getSortOrder(), block.getSortOrder()))
          .findFirst()
          .orElse(null);
      merged.add(replacement == null ? block : replacement);
    }
    merged.addAll(drafts.stream()
        .filter(block -> !draftOrders.contains(block.getSortOrder())
            || published.stream().noneMatch(publishedBlock -> Objects.equals(
                publishedBlock.getSortOrder(), block.getSortOrder())))
        .toList());
    return merged.stream()
        .sorted(Comparator.comparing(ContentBlockDTO::getSortOrder,
            Comparator.nullsLast(Comparator.naturalOrder())))
        .toList();
  }

  private void validateRequest(ContentDraftRequest request) throws ServiceException {
    if (request == null || request.slug() == null || !SLUG_PATTERN.matcher(request.slug()).matches()) {
      throw new ServiceException("Slug must contain lowercase letters, numbers, and hyphens only");
    }
    if (request.title() == null || request.title().isBlank()) {
      throw new ServiceException("Title is required");
    }
    for (ContentBlockInput block : request.blocks() == null ? List.<ContentBlockInput>of() : request.blocks()) {
      if (block.blockType() == null) {
        throw new ServiceException("Block type is required");
      }
    }
  }

  private void validateDocuments(List<ContentBlockInput> blocks) throws ServiceException {
    Set<Long> ids = new LinkedHashSet<>();
    for (ContentBlockInput block : blocks == null ? List.<ContentBlockInput>of() : blocks) {
      if (block.primaryDocumentId() != null) ids.add(block.primaryDocumentId());
      ids.addAll(block.secondaryDocumentIds() == null ? List.of() : block.secondaryDocumentIds());
    }
    documentResolver.validateDocumentIds(ids);
  }

  private void validateDocumentsFromDtos(List<ContentBlockDTO> blocks) throws ServiceException {
    Set<Long> ids = new LinkedHashSet<>();
    for (ContentBlockDTO block : blocks) {
      if (block.getPrimaryDocumentId() != null) ids.add(block.getPrimaryDocumentId());
      ids.addAll(parseIds(block.getSecondaryDocumentIds()));
    }
    documentResolver.validateDocumentIds(ids);
  }

  private ContentPageDTO requiredPage(Long pageId) throws ServiceException {
    ContentPageDTO page = pageService.get(pageId);
    if (page == null) throw new ServiceException("Content page not found: " + pageId);
    return page;
  }

  private String serializeIds(List<Long> ids) {
    return ids == null ? null : ids.stream().map(String::valueOf).collect(Collectors.joining(","));
  }

  private List<Long> parseIds(String value) throws ServiceException {
    if (value == null || value.isBlank()) return List.of();
    try {
      return java.util.Arrays.stream(value.split(","))
          .map(String::trim).filter(id -> !id.isBlank()).map(Long::valueOf).toList();
    } catch (NumberFormatException ex) {
      throw new ServiceException("Invalid secondary document ID list: " + value, ex);
    }
  }
}
