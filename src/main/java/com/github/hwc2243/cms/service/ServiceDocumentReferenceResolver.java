package com.github.hwc2243.cms.service;

import com.github.hwc2243.documentlibrary.dto.DocumentFileDTO;
import com.github.hwc2243.documentlibrary.service.DocumentFileService;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

@Service
public class ServiceDocumentReferenceResolver implements DocumentReferenceResolver {

  private final DocumentFileService documentFileService;
  private final DocumentAssetUrlProvider urlProvider;

  public ServiceDocumentReferenceResolver(
      DocumentFileService documentFileService,
      ObjectProvider<DocumentAssetUrlProvider> urlProvider) {
    this.documentFileService = Objects.requireNonNull(documentFileService);
    this.urlProvider = urlProvider.getIfAvailable(() -> documentFile -> null);
  }

  @Override
  public void validateDocumentIds(Collection<Long> documentIds) throws ServiceException {
    for (Long documentId : normalizedIds(documentIds)) {
      if (documentFile(documentId) == null) {
        throw new ServiceException("Document file not found: " + documentId);
      }
    }
  }

  @Override
  public Map<Long, AssetReference> resolveAssets(Collection<Long> documentIds)
      throws ServiceException {
    Map<Long, AssetReference> assets = new LinkedHashMap<>();
    for (Long documentId : normalizedIds(documentIds)) {
      DocumentFileDTO documentFile = documentFile(documentId);
      if (documentFile != null) {
        assets.put(documentId, new AssetReference(
            documentId,
            documentFile.getName(),
            documentFile.getMimeType(),
            urlProvider.urlFor(documentFile)));
      }
    }
    return assets;
  }

  private DocumentFileDTO documentFile(Long documentId) throws ServiceException {
    if (documentId == null) {
      return null;
    }
    try {
      return documentFileService.get(documentId);
    } catch (com.github.hwc2243.documentlibrary.service.ServiceException ex) {
      throw new ServiceException("Unable to resolve document file: " + documentId, ex);
    }
  }

  private Collection<Long> normalizedIds(Collection<Long> documentIds) {
    return documentIds == null ? Collections.emptyList() : documentIds;
  }
}
