package com.github.hwc2243.cms.service;

import com.github.hwc2243.documentlibrary.dto.DocumentFileDTO;

@FunctionalInterface
public interface DocumentAssetUrlProvider {

  String urlFor(DocumentFileDTO documentFile);
}
