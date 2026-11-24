package com.github.hwc2243.cms.service;

import com.github.hwc2243.cms.model.ContentPageStatus;
import java.time.LocalDateTime;
import java.util.List;

public record RenderedContentPage(
    Long id,
    String slug,
    String title,
    String summary,
    ContentPageStatus status,
    Integer version,
    LocalDateTime publishedAt,
    List<RenderedContentBlock> blocks
) {
}
