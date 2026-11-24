package com.github.hwc2243.cms.service;

import com.github.hwc2243.cms.model.ContentBlockBlockType;
import java.util.List;

public record RenderedContentBlock(
    Long id,
    Integer sortOrder,
    ContentBlockBlockType blockType,
    String heading,
    String source,
    String html,
    AssetReference primaryAsset,
    List<AssetReference> secondaryAssets,
    String metadataJson,
    Boolean visible,
    Long draftId,
    boolean draftAvailable,
    boolean draft
) {
}
