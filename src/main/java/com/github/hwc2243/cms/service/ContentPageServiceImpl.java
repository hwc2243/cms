package com.github.hwc2243.cms.service;

import com.github.hwc2243.cms.dto.ContentPageDTO;
import com.github.hwc2243.cms.entity.ContentPageEntity;
import com.github.hwc2243.cms.service.base.BaseContentPageServiceImpl;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContentPageServiceImpl
  extends BaseContentPageServiceImpl<ContentPageDTO, ContentPageEntity,Long>
  implements ContentPageService
{
  
  @Autowired
  protected ContentPageMapper contentPageMapper;

  
  protected ContentPageEntity toEntity (ContentPageDTO dto) {
    ContentPageEntity entity = contentPageMapper.toEntity(dto);
    return entity;
  }
  
  protected List<ContentPageEntity> toEntities (List<ContentPageDTO> dtos) {
    return contentPageMapper.toEntities(dtos);
  }

  protected ContentPageDTO toDto (ContentPageEntity entity) {
    return contentPageMapper.toDto(entity);
  }
  
  protected List<ContentPageDTO> toDtos (List<ContentPageEntity> entities) {
    return contentPageMapper.toDtos(entities);
  }

}
