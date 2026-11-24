package com.github.hwc2243.cms.service;

public record AssetReference(
    Long documentId,
    String filename,
    String contentType,
    String url
) {
}
