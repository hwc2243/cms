package com.github.hwc2243.cms.service;

import com.github.hwc2243.cms.dto.ContentBlockDTO;
import com.github.hwc2243.cms.entity.ContentBlockEntity;
import com.github.hwc2243.cms.entity.ContentPageEntity;
import com.github.hwc2243.cms.persistence.ContentPagePersistence;
import com.github.hwc2243.cms.service.base.BaseContentBlockServiceImpl;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContentBlockServiceImpl
  extends BaseContentBlockServiceImpl<ContentBlockDTO, ContentBlockEntity,Long>
  implements ContentBlockService
{
  
  @Autowired
  protected ContentBlockMapper contentBlockMapper;

  @Autowired
  protected ContentPagePersistence pagePersistence;
  
  protected ContentBlockEntity toEntity (ContentBlockDTO dto) {
    ContentBlockEntity entity = contentBlockMapper.toEntity(dto);
    entity.setPage(dto.getPageId() == null
        ? null
        : pagePersistence.getReferenceById(dto.getPageId()));
    return entity;
  }
  
  protected List<ContentBlockEntity> toEntities (List<ContentBlockDTO> dtos) {
    return contentBlockMapper.toEntities(dtos);
  }

  protected ContentBlockDTO toDto (ContentBlockEntity entity) {
    return contentBlockMapper.toDto(entity);
  }
  
  protected List<ContentBlockDTO> toDtos (List<ContentBlockEntity> entities) {
    return contentBlockMapper.toDtos(entities);
  }

}
