package com.github.hwc2243.cms.service;

import java.util.List;

public record ContentDraftRequest(
    String slug,
    String title,
    String summary,
    List<ContentBlockInput> blocks
) {
}
