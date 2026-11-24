package com.github.hwc2243.cms.service;

import com.github.hwc2243.cms.model.ContentBlockBlockType;
import java.util.List;

public record ContentBlockInput(
    Integer sortOrder,
    ContentBlockBlockType blockType,
    String heading,
    String body,
    Long primaryDocumentId,
    List<Long> secondaryDocumentIds,
    String metadataJson,
    Boolean visible
) {
}
