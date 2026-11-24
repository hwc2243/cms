package com.github.hwc2243.cms.service;

import java.util.Collection;
import java.util.Map;

public interface DocumentReferenceResolver {

  void validateDocumentIds(Collection<Long> documentIds) throws ServiceException;

  Map<Long, AssetReference> resolveAssets(Collection<Long> documentIds)
      throws ServiceException;
}
