package com.github.hwc2243.cms.service;



import com.github.hwc2243.cms.dto.ContentBlockDTO;
import com.github.hwc2243.cms.dto.ContentPageDTO;
import com.github.hwc2243.cms.entity.ContentBlockEntity;
import com.github.hwc2243.cms.entity.ContentPageEntity;
import java.util.List;
import java.util.Set;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.SubclassMapping;

@Mapper(componentModel = "spring")
public interface ContentBlockMapper {

  @Named("contentBlockDefault")
  @Mapping(target = "pageId", source = "page.id")
  ContentBlockDTO toDto(ContentBlockEntity entity);

  @Named("contentBlockShallow")
  ContentBlockDTO toDtoShallow(ContentBlockEntity entity);



  ContentBlockEntity toEntity(ContentBlockDTO dto);

  @IterableMapping(qualifiedByName = "contentBlockDefault")
  List<ContentBlockDTO> toDtos(List<ContentBlockEntity> entities);

  @IterableMapping(qualifiedByName = "contentBlockShallow")
  List<ContentBlockDTO> toDtosShallow(List<ContentBlockEntity> entities);

  List<ContentBlockEntity> toEntities(List<ContentBlockDTO> dtos);
}
