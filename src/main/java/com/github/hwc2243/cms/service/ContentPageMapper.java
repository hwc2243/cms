package com.github.hwc2243.cms.service;



import com.github.hwc2243.cms.dto.ContentPageDTO;
import com.github.hwc2243.cms.entity.ContentPageEntity;
import java.util.List;
import java.util.Set;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.SubclassMapping;

@Mapper(componentModel = "spring")
public interface ContentPageMapper {

  @Named("contentPageDefault")
  ContentPageDTO toDto(ContentPageEntity entity);

  @Named("contentPageShallow")
  ContentPageDTO toDtoShallow(ContentPageEntity entity);



  ContentPageEntity toEntity(ContentPageDTO dto);

  @IterableMapping(qualifiedByName = "contentPageDefault")
  List<ContentPageDTO> toDtos(List<ContentPageEntity> entities);

  @IterableMapping(qualifiedByName = "contentPageShallow")
  List<ContentPageDTO> toDtosShallow(List<ContentPageEntity> entities);

  List<ContentPageEntity> toEntities(List<ContentPageDTO> dtos);
}
